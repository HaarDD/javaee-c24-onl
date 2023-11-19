<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="by.teachmeskills.lesson24.SuperHeroCard" %>
<%@ page import="static by.teachmeskills.lesson24.SuperHeroesServlet.superHeroCardList" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8"/>
    <link rel="stylesheet" href="<c:url value="/css/lesson24/superheroes.css"/>">
    <title>Супергерои</title>
</head>

<body>
<div class="superhero-card-collection">
    <%
        for (SuperHeroCard superHeroCard : superHeroCardList) {
    %>
    <div class="superhero-card">
        <div class="superhero-name">
            <%=superHeroCard.getName()%>
        </div>
        <div style="background-image: url('images/lesson24/superheroes/<%=File.separator + superHeroCard.getImgName()%>');"
             class="superhero-img">
        </div>
        <div class="superhero-description">
            <div class="superhero-specs">
                <div class="superhero-skills">
                    <div class="superhero-skill">
                        <div class="superhero-skill-item <%=superHeroCard.getSpiderHandSkill()>=1 ? "hand" : "empty"%>-icon"></div>
                        <div class="superhero-skill-item <%=superHeroCard.getSpiderHandSkill()>=2 ? "hand" : "empty"%>-icon"></div>
                        <div class="superhero-skill-item <%=superHeroCard.getSpiderHandSkill()>=3 ? "hand" : "empty"%>-icon"></div>
                    </div>
                    <div class="superhero-skill">
                        <div class="superhero-skill-item <%=superHeroCard.getPumpkinSkill()>=1 ? "pumpkin" : "empty"%>-icon"></div>
                        <div class="superhero-skill-item <%=superHeroCard.getPumpkinSkill()>=2 ? "pumpkin" : "empty"%>-icon"></div>
                        <div class="superhero-skill-item <%=superHeroCard.getPumpkinSkill()>=3 ? "pumpkin" : "empty"%>-icon"></div>
                    </div>
                    <div class="superhero-skill">
                        <div class="superhero-skill-item <%=superHeroCard.getSunSkill()>=1 ? "sun" : "empty"%>-icon"></div>
                        <div class="superhero-skill-item <%=superHeroCard.getSunSkill()>=2 ? "sun" : "empty"%>-icon"></div>
                        <div class="superhero-skill-item <%=superHeroCard.getSunSkill()>=3 ? "sun" : "empty"%>-icon"></div>
                    </div>
                </div>
                <div class="superhero-properties">
                    <div class="superhero-property">
                        <div class="superhero-property-name">интеллект</div>
                        <div class="superhero-property-number"><%=superHeroCard.getIntelligenceProperty()%>
                        </div>
                    </div>
                    <div class="superhero-property">
                        <div class="superhero-property-name">сила</div>
                        <div class="superhero-property-number"><%=superHeroCard.getStrengthSkillsProperty()%>
                        </div>
                    </div>
                    <div class="superhero-property">
                        <div class="superhero-property-name">скорость и ловкость</div>
                        <div class="superhero-property-number"><%=superHeroCard.getSpeedAgilitySkillsProperty()%>
                        </div>
                    </div>
                    <div class="superhero-property">
                        <div class="superhero-property-name">особые умения</div>
                        <div class="superhero-property-number"><%=superHeroCard.getSpecialSkillsProperty()%>
                        </div>
                    </div>
                    <div class="superhero-property">
                        <div class="superhero-property-name">бойцовские навыки</div>
                        <div class="superhero-property-number"><%=superHeroCard.getFightingSkillsProperty()%>
                        </div>
                    </div>
                </div>
            </div>
            <div class="superhero-story">
                <div class="superhero-story-text">
                    <%=superHeroCard.getDescription()%>
                </div>
            </div>

        </div>

    </div>
    <%
        }
    %>


</div>
</body>

</html>
