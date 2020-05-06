<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<div class="container">
    <div class="row">
        <div class="col-12">
            <form class="pl-3" method="post" action="resume" id="resumeForm"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="uuid" value="${resume.uuid}">
                <dl class="row">
                    <dt class="col-1 align-middle">Имя:</dt>
                    <dd class="col-11"><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
                </dl>
                <h3>Контакты</h3>
                <div class="pl-4">
                    <c:forEach var="type" items="<%=ContactType.values()%>">
                        <dl class="row">
                            <dt class="col-3 align-middle text-nowrap">${type.title}</dt>
                            <dd class="col-9"><input type="text" name="${type.name()}" size="100"
                                                     value="${resume.getContact(type)}"></dd>
                        </dl>
                    </c:forEach>
                </div>
                <c:forEach var="sectionEntry" items="${resume.sections}">
                    <jsp:useBean id="sectionEntry"
                                 type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
                    <h3><%=sectionEntry.getKey().getTitle()%>
                    </h3>
                    <c:choose>
                        <c:when test="${sectionEntry.key.name() == 'PERSONAL' || sectionEntry.key.name() == 'OBJECTIVE'}">
                            <div class="pl-4">
                        <textarea name="${sectionEntry.key.name()}" cols="86" rows="3"
                                  maxlength="500">${(sectionEntry.value.getText())}</textarea>
                            </div>

                        </c:when>
                        <c:when test="${sectionEntry.key.name() == 'ACHIEVEMENTS' || sectionEntry.key.name() == 'QUALIFICATIONS'}">
                            <ul>
                                <c:forEach var="item" items="${sectionEntry.value.getList()}">
                                    <%--                            <li><input type="text" name="${sectionEntry.key.name()}_${'item'}" size="100"--%>
                                    <%--                                       value="${item}"></li>--%>
                                    <li><textarea name="${sectionEntry.key.name()}_${'item'}" cols="86" rows="3"
                                                  maxlength="500">${item}</textarea></li>
                                </c:forEach>
                            </ul>
                        </c:when>
                        <c:when test="${sectionEntry.key.name() == 'EXPERIENCE' || sectionEntry.key.name() == 'EDUCATION'}">

                            <c:forEach var="organization" items="${sectionEntry.value.getList()}"
                                       varStatus="orgLoop">
                                <div class="pl-4 container-fluid">
                                    <table class="table table-striped">
                                        <tr class="row">
                                            <td class="col-sm-1">Название:</td>
                                            <td class="col-sm-9">
                                                <input type="text"
                                                       name="${sectionEntry.key.name()}_${'companyName'}"
                                                       size="100" value="${organization.getCompanyName()}"><br>
                                            </td>
                                        </tr>
                                        <tr class="row">
                                            <td class="col-sm-1"> URL:</td>
                                            <td class="col-sm-9">
                                                <input type="text"
                                                       name="${sectionEntry.key.name()}_${orgLoop.index}_${'url'}"
                                                       size="100" value="${organization.getUrl()}">
                                            </td>
                                        </tr>
                                        <c:forEach var="positionDetail"
                                                   items="${organization.getPositionDetailsList()}"
                                                   varStatus="posLoop">
                                            <tr class="row">
                                                <td class="col-sm-3">
                                                    c: <input type="month"
                                                              name="${sectionEntry.key.name()}_${orgLoop.index}_${posLoop.index}_${'endDate'}"
                                                              value="${DateUtil.toHtmlCalendarFormat(positionDetail.getEndDate())}"><br>
                                                    до: <input type="month"
                                                               name="${sectionEntry.key.name()}_${orgLoop.index}_${posLoop.index}_${'startDate'}"
                                                               value="${DateUtil.toHtmlCalendarFormat(positionDetail.getStartDate())}">
                                                </td>
                                                <td class="col-sm-8">
                                                    <input type="text"
                                                           name="${sectionEntry.key.name()}_${orgLoop.index}_${'title'}"
                                                           size="86"
                                                           value="${positionDetail.getTitle()}"><br>
                                                    <b>Описание:</b><br>
                                                    <textarea
                                                            name="${sectionEntry.key.name()}_${orgLoop.index}_${posLoop.index}_${'description'}"
                                                            cols="86" rows="3"
                                                            maxlength="500">${positionDetail.getDescription()}</textarea>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                                <hr>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </c:forEach>
                <button type="submit">Сохранить</button>
                <button onclick="window.history.back()">Отменить</button>
            </form>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
