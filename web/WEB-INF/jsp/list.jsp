<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div class="container">
        <div class="row">
            <table class="table table-striped col-12">
                <thead>
                <tr>
                    <th class="w-15">Имя</th>
                    <th class="w-75">Email</th>
                    <th></th>
                    <th class="w-5"></th>
                    <th class="w-5"></th>
                </tr>
                </thead>
                <c:forEach items="${resumes}" var="resume">
                    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
                    <tr>
                        <td class="align-middle text-nowrap"><a
                                href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                        </td>
                        <td class="align-middle text-left"><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                        </td>
                        <td class=""></td>
                        <td class="text-right"><a href="resume?uuid=${resume.uuid}&action=delete">
                            <button type="button" class="btn btn-danger">Delete</button>
                        </a></td>
                        <td class="text-right"><a href="resume?uuid=${resume.uuid}&action=edit">
                            <button type="button" class="btn btn-warning">Edit</button>
                        </a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
