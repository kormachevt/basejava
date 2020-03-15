package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void write(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                /**
                 * 1. записать тип(текст/список/список организаций)
                 * 2. в зависимости от типа записать присущие ему поля
                 *
                 */
                String sectionClassName = getSectionName(entry.getValue());
                dos.writeUTF(sectionClassName);
                dos.writeUTF(entry.getKey().name());
                switch (sectionClassName) {
                    case "TextSection":
                        dos.writeUTF(((TextSection) entry.getValue()).getText());
                        break;
                    case "ListSection":
                        List<String> list = ((ListSection) entry.getValue()).getList();
                        dos.writeInt(list.size());
                        for (int i = 0; i < list.size(); i++) {
                            dos.writeUTF(list.get(i));
                        }
                        break;
                    case "OrganizationSection":
                        List<Organization> orgList = ((OrganizationSection) entry.getValue()).getList();
                        dos.writeInt(orgList.size());
                        for (int j = 0; j < orgList.size(); j++) {
                            printOrganization(orgList.get(j), dos);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionClassName);
                }

            }
//            TODO: implements Section
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsSize = dis.readInt();
            for (int i = 0; i < contactsSize; i++) {
                resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionsSize = dis.readInt();
            for (int i = 0; i < sectionsSize; i++) {
                /**
                 * 1. определить тип секции
                 * 2. в зависимости  от тип прочитать поля
                 */
                String sectionClassName = dis.readUTF();
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                Section section;
                switch (sectionClassName) {
                    case "TextSection":
                        section = new TextSection(dis.readUTF());
                        break;
                    case "ListSection":
                        int listSize = dis.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < listSize; j++) {
                            list.add(dis.readUTF());
                        }
                        section = new ListSection(list);
                        break;
                    case "OrganizationSection":
                        int orgListSize = dis.readInt();
                        List<Organization> orgList = new ArrayList<>();
                        for (int m = 0; m < orgListSize; m++) {
                            orgList.add(parseOrganization(dis));
                        }
                        section = new OrganizationSection(orgList);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionClassName);
                }
                resume.setSection(sectionType, section);
            }
            return resume;
        }
    }

    /**
     * Определяет тип секции
     *
     * @param section
     * @param <T>
     * @return
     */
    private <T extends Section> String getSectionName(T section) {
        return section.getClass().getSimpleName();
    }

    /**
     * Возврашает поля класса переданнной секции
     *
     * @param section
     * @param <T>
     * @return
     */
    private <T extends Section> Field[] getFields(T section) {
        return section.getClass().getDeclaredFields();
    }

    /**
     * Метод печатает поля организации
     */
    private void printOrganization(Organization org, DataOutputStream dos) throws IOException {
        dos.writeUTF(org.getCompanyName());
        dos.writeUTF(org.getUrl() == null ? "" : org.getUrl());
        List<Organization.PositionDetails> positionDetailsList = org.getPositionDetailsList();
        dos.writeInt(positionDetailsList.size());
        for (int i = 0; i < org.getPositionDetailsList().size(); i++) {
            printPositionDetails(positionDetailsList.get(i), dos);
        }
    }

    private Organization parseOrganization(DataInputStream dis) throws IOException {
        String _url = dis.readUTF();
        String _companyName = dis.readUTF();
        String url = _url.equals("") ? null : _url;
        String companyName = _companyName.equals("") ? null : _companyName;
        Organization organization = new Organization(url, companyName);
        int positionDetailsListSize = dis.readInt();
        for (int i = 0; i < positionDetailsListSize; i++) {
            String _title = dis.readUTF();
            String title  = _title.equals("") ? null : _title;
            String _description = dis.readUTF();
            String description = _description.equals("") ? null : _description;
            LocalDate startDate = LocalDate.parse(dis.readUTF());
            LocalDate endDate = LocalDate.parse(dis.readUTF());
            organization.addPositionDetails(title, startDate, endDate, description);
        }
        return organization;
    }

    private void printPositionDetails(Organization.PositionDetails positionDetails, DataOutputStream dos) throws IOException {
        dos.writeUTF(positionDetails.getTitle());
        dos.writeUTF(positionDetails.getDescription() == null ? "" : positionDetails.getDescription());
        dos.writeUTF(positionDetails.getStartDate().toString());
        dos.writeUTF(positionDetails.getEndDate().toString());
    }
}
