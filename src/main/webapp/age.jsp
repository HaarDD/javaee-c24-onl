<%--
  Created by IntelliJ IDEA.
  User: administrator
  Date: 23.10.2023
  Time: 0:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Enter your age!</h2>
<form action="http://localhost:8081/javaee_c24_onl_war_exploded/age_result" method="post">
    <input name="age" value="" placeholder="Your age"/>
    <input type="submit"/>
</form>
</body>
</html>
