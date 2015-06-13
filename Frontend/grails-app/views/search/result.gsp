<%--
  Created by IntelliJ IDEA.
  User: rahul
  Date: 13/06/15
  Time: 12:15 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'newcss.css')}" type="text/css">
    <g:javascript src="jquery.js"/>
    <g:javascript src="packery.pkgd.min.js"/>
    <g:javascript src="asd.js"/>

</head>

<body class="HolyGraillayout">

<header class="layout-header"><h2>Title</h2></header>

<div class="layout-body" draggable="True">

    <div class="layout-nav"></div>

    <div class="packery ">
        
        
        <g:each in="${results}" var="result">
            <div class="${result.relevance_group} item">
                <div class="item-content" >${result.relevance_group}</div>
            </div>
        </g:each>
        


    </div>

    <div class="layout-ad"></div>
    
</div>

<footer class="layout-footer"><h3>Footer</h3></footer>




</body>
</html>