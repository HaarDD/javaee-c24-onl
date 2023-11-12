<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TMS logo</title>
</head>
<style>
    @font-face {
        font-family: 'Avenir';
        src: url('<c:url value='/css/lesson24/AvenirNextRoundedStd-Med.ttf'/>') format('truetype');
    }

    @font-face {
        font-family: 'EuroMachina';
        src: url('<c:url value='/css/lesson24/EuroMachina.ttf'/>') format('truetype');
    }

    body {
        font-family: "Avenir", sans-serif;
        font-size: 10rem;
    }

    .characters-container {
        display: flex;
        background-color: black;
        color: white;
        width: fit-content;
        margin: 1rem;
        padding: 2rem;
    }

    .characters-first, .characters-third {
        font-family: "EuroMachina", sans-serif;
        color: yellow;
    }

    .characters-second > span {
        margin-left: 1.5rem;
    }

    .characters-third {
        align-self: end;
    }
</style>
<body>
<div class="characters-container">
    <div class="characters-first">
        <
    </div>
    <div class="characters-second">
        Teach<br/>
        <span>Me</span><br/>
        <span>Skills</span><br/>
    </div>
    <div class="characters-third">
        />
    </div>
</div>

</body>
</html>
