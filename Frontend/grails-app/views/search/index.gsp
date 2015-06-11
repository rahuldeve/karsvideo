<%--
  Created by IntelliJ IDEA.
  User: rahul
  Date: 10/06/15
  Time: 2:48 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

<g:form controller="search" action="query">
    <label>Search:</label>
    <g:textField name="query"/>
    <g:submitButton name="Search"/>
</g:form>

</body>
</html>