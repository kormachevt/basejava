<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <h3>Контакты:</h3>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <p>
                <jsp:useBean id="sectionEntry"
                             type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
        <h3><%=sectionEntry.getKey().getTitle()%>
        </h3>
        <c:choose>
            <c:when test="${sectionEntry.key.name() == 'PERSONAL' || sectionEntry.key.name() == 'OBJECTIVE'}">
                <p><%=((TextSection) sectionEntry.getValue()).getText()%>
                </p>
            </c:when>
            <c:when test="${sectionEntry.key.name() == 'ACHIEVEMENTS' || sectionEntry.key.name() == 'QUALIFICATIONS'}">
                <ul>
                    <c:forEach var="item" items="${sectionEntry.value.getList()}">
                        <li>${item}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${sectionEntry.key.name() == 'EXPERIENCE' || sectionEntry.key.name() == 'EDUCATION'}">
                <c:forEach var="organization" items="${sectionEntry.value.getList()}">
                    <tr>
                        <td>
                            <h3><a href="${organization.getUrl()}">${organization.getCompanyName()}</a></h3>
                        </td>
                    </tr>
                    <c:forEach var="positionDetail" items="${organization.getPositionDetailsList()}">
                        <tr>
                            <td>${DateUtil.format(positionDetail.getStartDate())}
                                - ${DateUtil.format(positionDetail.getEndDate())}</td>
                            <td><b>${positionDetail.getTitle()}</b>.<br>${positionDetail.getDescription()}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </c:when>
        </c:choose>
        </p>
    </c:forEach>
</section>

<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
