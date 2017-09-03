<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Поиск резюме</title>
</head>
<body>
<div>
    <form action="?action=parse" method="post">
        <input type="submit" value="Найти резюме">
    </form>
    <form action="?action=filter" method="post">
        <label>Должность</label>
        <input type="text" name="header" value="${param.header}">
        <button type="submit">Фильтр</button>
    </form>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Желаемая должность</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="org.alesapps.xmrtask.model.Resume"/>
            <tr>
                <td><a href="http://www.zarplata.ru${resume.url}">${resume.header}</a></td>
            </tr>
        </c:forEach>
        </thead>
    </table>
</div>
</body>
</html>