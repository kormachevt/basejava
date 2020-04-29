package ru.javawebinar.basejava.web;

import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ResumesServlet extends HttpServlet {
    private Storage storage; // = Config.get().getStorage();

    @Override
    public void init() {
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.getWriter().write(buildResumesPage());
    }

    private String buildResumesPage() {
        List<Resume> resumes = storage.getAllSorted();
        StringBuilder sb = new StringBuilder();
        sb.append("<table>");
        sb.append("<tr><th>uuid</th><th>fullName</th></tr>");
        for (Resume resume : resumes) {
            sb.append(String.format("<tr><td>%s</td><td>%s</td></tr> ", resume.getUuid(), resume.getFullName()));
        }
        sb.append("</table>");
        return sb.toString();
    }
}
