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
    <g:javascript src="draggabilly.pkgd.min.js"/>
    <g:javascript src="asd.js"/>

</head>

<body class="HolyGraillayout">

<header class="layout-header"><h2>Title</h2></header>

<div class="layout-body">

    <div class="layout-nav"></div>

    <div class="packery ">
        
        
        <g:each in="${results}" var="result">
            <div class="${result.relevance_group} item">
                <div class="item-content" >
                    <div class="container">

                        <div class="container-first-row">
                            <div class="rel_score">${result.relevance}</div>
                            <div class="vid_img"></div>
                            <div class="vid_details"></div>
                            <div class="vid_keywords"></div>
                        </div>

                        <div class="segbar">
                            <div class="seg"></div>
                        </div>

                        <div class="container-third-row"></div>

                    </div>
                </div>
            </div>
        </g:each>
        


    </div>

    <div class="layout-ad"></div>
    *
</div>

<footer class="layout-footer"><h3>Footer</h3></footer>




</body>
</html>