<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" id="resumeForm" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size="30" value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>


        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
            <h3><%=sectionEntry.getKey().getTitle()%>
            </h3>
            <c:choose>
                <c:when test="${sectionEntry.key.name() == 'PERSONAL' || sectionEntry.key.name() == 'OBJECTIVE'}">
                    <textarea name="${sectionEntry.key.name()}" cols="86" rows="3"
                              maxlength="500">${(sectionEntry.value.getText())}</textarea>
                </c:when>
                <c:when test="${sectionEntry.key.name() == 'ACHIEVEMENTS' || sectionEntry.key.name() == 'QUALIFICATIONS'}">
                    <ul>
                        <c:forEach var="item" items="${sectionEntry.value.getList()}">
                            <li><input type="text" name="${sectionEntry.key.name()}_${'item'}" size="30"
                                       value="${item}"></li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:when test="${sectionEntry.key.name() == 'EXPERIENCE' || sectionEntry.key.name() == 'EDUCATION'}">
                    <ul>
                        <c:forEach var="organization" items="${sectionEntry.value.getList()}" varStatus="orgLoop">
                            <tr>
                                <td>
                                    <input type="text"
                                           name="${sectionEntry.key.name()}_${'companyName'}"
                                           size="30" value="${organization.getCompanyName()}"><br>
                                    <input type="text"
                                           name="${sectionEntry.key.name()}_${orgLoop.index}_${'url'}"
                                           size="30" value="${organization.getUrl()}">
                                </td>
                                <c:forEach var="positionDetail" items="${organization.getPositionDetailsList()}"
                                           varStatus="posLoop">
                                    <ul>

                                        <li>
                                            <input type="text"
                                                   name="${sectionEntry.key.name()}_${orgLoop.index}_${'title'}"
                                                   size="30"
                                                   value="${positionDetail.getTitle()}">
                                        </li>
                                        <li>
                                            <input type="month"
                                                   name="${sectionEntry.key.name()}_${orgLoop.index}_${posLoop.index}_${'startDate'}"
                                                   value="${DateUtil.toHtmlCalendarFormat(positionDetail.getStartDate())}">
                                            -
                                            <input type="month"
                                                   name="${sectionEntry.key.name()}_${orgLoop.index}_${posLoop.index}_${'endDate'}"
                                                   value="${DateUtil.toHtmlCalendarFormat(positionDetail.getEndDate())}">
                                        </li>
                                        <li>
                                            <textarea
                                                    name="${sectionEntry.key.name()}_${orgLoop.index}_${posLoop.index}_${'description'}"
                                                    cols="86" rows="3"
                                                    maxlength="500">${positionDetail.getDescription()}</textarea>
                                        </li>
                                    </ul>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                    </ul>
                </c:when>
            </c:choose>
        </c:forEach>


        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
