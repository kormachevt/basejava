package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.storage.Storage;
import ru.javawebinar.basejava.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.basejava.model.SectionType.*;
import static ru.javawebinar.basejava.util.StringUtils.isNotEmptyString;

public class ResumesServlet extends HttpServlet {
    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init() {
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        boolean isCreate = "".equals(uuid);
        Resume resume = isCreate ? new Resume(fullName) : storage.get(uuid);
        resume.setFullName(fullName);
        readContacts(resume, request);
        readSections(resume, request);
        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        String[] sectionsToAdd = request.getParameterValues("addNewItemToSection");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                break;
            case "edit":
                resume = isNotEmptyString(uuid) ? storage.get(uuid) : new Resume();
                if (sectionsToAdd != null && sectionsToAdd.length != 0) {
                    addItemsToSections(resume, sectionsToAdd);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + "is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    private void readContacts(Resume resume, HttpServletRequest request) {
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (isNotEmptyString(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }

        }
    }

    private void readSections(Resume resume, HttpServletRequest request) {
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    readTextSection(resume, type, request);
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    readListSection(resume, type, request);
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    readOrganizationSection(resume, type, request);
                    break;
                default:
                    throw new IllegalArgumentException("Section type " + type.name() + " is illegal");
            }
        }
    }

    private void readTextSection(Resume resume, SectionType type, HttpServletRequest request) {
        String value = request.getParameter(type.name());
        if (isNotEmptyString(value)) {
            resume.addSection(type, new TextSection(value));
        } else {
            resume.getSections().remove(type);
        }
    }

    private void readListSection(Resume resume, SectionType type, HttpServletRequest request) {
        String[] items = request.getParameterValues(type.name() + "_item");
        List<String> itemsList = new ArrayList<>();
        if (items != null && items.length != 0) {
            for (String item : items) {
                if (isNotEmptyString(item)) {
                    itemsList.add(item);
                }
            }
            resume.addSection(type, new ListSection(itemsList));
        } else {
            resume.getSections().remove(type);
        }
    }

    private void readOrganizationSection(Resume resume, SectionType type, HttpServletRequest request) {
        String[] organizationNames = request.getParameterValues(type.name() + "_companyName");
        List<Organization> organizationList = new ArrayList<>();
        if (organizationNames != null && organizationNames.length != 0) {
            for (int i = 0; i < organizationNames.length; i++) {
                List<Organization.PositionDetails> positionDetailsList = new ArrayList<>();
                String url = request.getParameter(type.name() + "_" + i + "_" + "url");
                String[] titles = request.getParameterValues(type.name() + "_" + i + "_" + "title");
                Organization.PositionDetails positionDetails;
                for (int j = 0; j < titles.length; j++) {
                    LocalDate startDate = DateUtil.parseFromHtmlCalendar(request.getParameter(type.name() + "_" + i + "_" + j + "_" + "startDate"));
                    LocalDate endDate = DateUtil.parseFromHtmlCalendar(request.getParameter(type.name() + "_" + i + "_" + j + "_" + "endDate"));
                    String description = request.getParameter(type.name() + "_" + i + "_" + j + "_" + "description");
                    positionDetails = new Organization.PositionDetails(titles[j], startDate, endDate, description);
                    positionDetailsList.add(positionDetails);
                }
                Organization organization = new Organization(organizationNames[i], url);
                organization.setPositionDetailsList(positionDetailsList);
                organizationList.add(organization);
            }
            resume.addSection(type, new OrganizationSection(organizationList));
        } else {
            resume.getSections().remove(type);
        }

    }

    private void addItemsToSections(Resume resume, String[] sectionsToAdd) {
        for (String section : sectionsToAdd) {
            SectionType type = SectionType.valueOf(section);
            if (type == ACHIEVEMENTS || type == QUALIFICATIONS) {
                ListSection oldListSection = (ListSection) resume.getSections().get(type);
                boolean isEmpty = oldListSection == null;
                ListSection listSection = isEmpty ? new ListSection(new ArrayList<>()) : oldListSection;
                listSection.addItem("");
                resume.addSection(type, listSection);
            } else if (type == EDUCATION || type == EXPERIENCE) {
                OrganizationSection oldOrganizationSection = (OrganizationSection) resume.getSections().get(type);
                boolean isEmpty = oldOrganizationSection == null;
                OrganizationSection organizationSection = isEmpty ? new OrganizationSection(new ArrayList<>()) : oldOrganizationSection;
                organizationSection.addOrganization(new Organization("", "")
                                                            .addPositionDetails(new Organization.PositionDetails("",
                                                                                                                 LocalDate.of(3000, 1, 1),
                                                                                                                 LocalDate.of(3000, 1, 1),
                                                                                                                 "")));
                resume.addSection(type, organizationSection);
            } else {
                throw new IllegalArgumentException("Type " + type.name() + "is illegal");
            }
        }
    }
}
