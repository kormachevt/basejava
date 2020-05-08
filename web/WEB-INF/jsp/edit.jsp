<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
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
<c:set var="queryString"
       value="<%= request.getAttribute(\"javax.servlet.forward.request_uri\") + \"?\" + request.getQueryString()%>"
       scope="session"/>
<div class="container">
    <div class="row">
        <div class="col-12">
            <form class="pl-3" method="post" action="resume" id="resumeForm"
                  enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="uuid" value="${resume.uuid}">
                <dl class="row">
                    <dt class="col-1 align-middle">Имя:</dt>
                    <dd class="col-11"><input type="text" name="fullName" size=50 value="${resume.fullName}" required>
                    </dd>
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
                <c:forEach var="sectionType" items="${SectionType.values()}">
                    <jsp:useBean id="sectionType" type="ru.javawebinar.basejava.model.SectionType"/>
                    <c:set var="sections" scope="session" value="${resume.sections}"/>
                    <h3>${sectionType.title}
                    </h3>
                    <c:choose>
                        <c:when test="${sectionType == 'PERSONAL' || sectionType == 'OBJECTIVE'}">
                            <div class="pl-4">
                                <textarea name="${sectionType}" cols="86" rows="3"
                                          maxlength="500">${(sections.get(sectionType).getText())}</textarea>
                            </div>
                        </c:when>
                        <c:when test="${sectionType == 'ACHIEVEMENTS' || sectionType == 'QUALIFICATIONS'}">
                            <ul>
                                <c:forEach var="item" items="${sections.get(sectionType).getList()}">
                                    <li><textarea name="${sectionType}_${'item'}" cols="86" rows="3"
                                                  maxlength="500">${item}</textarea></li>
                                </c:forEach>
                            </ul>
                            <a href="${queryString}&addNewItemToSection=${sectionType}">
                                <button type="button" class="btn btn-success ml-5 mb-4">Add</button>
                            </a>
                        </c:when>
                        <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">

                            <c:forEach var="organization" items="${sections.get(sectionType).getList()}"
                                       varStatus="orgLoop">
                                <div class="pl-4 container-fluid">
                                    <table class="table table-striped">
                                        <tr class="row">
                                            <td class="col-sm-1">Название:</td>
                                            <td class="col-sm-9">
                                                <input type="text"
                                                       name="${sectionType}_${'companyName'}"
                                                       size="100" value="${organization.getCompanyName()}"><br>
                                            </td>
                                        </tr>
                                        <tr class="row">
                                            <td class="col-sm-1"> URL:</td>
                                            <td class="col-sm-9">
                                                <input type="text"
                                                       name="${sectionType}_${orgLoop.index}_${'url'}"
                                                       size="100" value="${organization.getUrl()}">
                                            </td>
                                        </tr>
                                        <c:forEach var="positionDetail"
                                                   items="${organization.getPositionDetailsList()}"
                                                   varStatus="posLoop">
                                            <tr class="row">
                                                <td class="col-sm-3">
                                                    c: <input type="month"
                                                              name="${sectionType}_${orgLoop.index}_${posLoop.index}_${'startDate'}"
                                                              value="${DateUtil.toHtmlCalendarFormat(positionDetail.getEndDate())}"><br>
                                                    до: <input type="month"
                                                               name="${sectionType}_${orgLoop.index}_${posLoop.index}_${'endDate'}"
                                                               value="${DateUtil.toHtmlCalendarFormat(positionDetail.getStartDate())}">
                                                </td>
                                                <td class="col-sm-8">
                                                    <input type="text"
                                                           name="${sectionType}_${orgLoop.index}_${'title'}"
                                                           size="86"
                                                           value="${positionDetail.getTitle()}"><br>
                                                    <b>Описание:</b><br>
                                                    <textarea
                                                            name="${sectionType}_${orgLoop.index}_${posLoop.index}_${'description'}"
                                                            cols="86" rows="3"
                                                            maxlength="500">${positionDetail.getDescription()}</textarea>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                                <hr>
                            </c:forEach>
                            <a href="${queryString}&addNewItemToSection=${sectionType}">
                                <button type="button" class="btn btn-success ml-5 mb-4">Add</button>
                            </a>
                        </c:when>
                    </c:choose>

                </c:forEach>
            </form>
            <div class="mt-4 mb-2">
                <button class="btn btn-primary btn-lg" type="submit" form="resumeForm">Save</button>
                <button class="btn btn-secondary btn-lg" onclick="window.history.back()">Cancel</button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
