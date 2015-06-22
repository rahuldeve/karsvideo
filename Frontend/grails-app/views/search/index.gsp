<%--
  Created by IntelliJ IDEA.
  User: rahul
  Date: 10/06/15
  Time: 2:48 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <link rel="stylesheet" href="${resource(dir:'css', file: 'landingpage.css')}"/>
    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    <title></title>
</head>

<body>
<header></header>
<div class="flex-container nav">
    <g:form controller="search" action="query" class="flex-item search">

        <g:textField name="query" class="search_text" placeholder="Search" />
        <g:submitButton name="Search" class="search_button"></g:submitButton>
    </g:form>
</div>

</body>
</html>