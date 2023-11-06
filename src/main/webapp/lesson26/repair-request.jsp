<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="by.teachmeskills.lesson26.dto.RepairRequest" %>
<%@ page import="java.util.List" %>
<%@ page import="by.teachmeskills.lesson26.SaveRequestServlet" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="lesson26/css/bootstrap.min.css" rel="stylesheet">
    <title>Список заявок</title>
</head>
<body>
<jsp:include page="request-navbar.jsp"/>
<div class="container">
    <%
        List<RepairRequest> repairRequestList = (List<RepairRequest>) session.getAttribute("repair_request");
        if (repairRequestList == null || repairRequestList.isEmpty()) {
    %>
    <h1>Заявок нет</h1>
    <%
    } else {
    %>
    <h1>Список заявок</h1>
    <div class="row g-3">
        <div class="row-md-8">
            <%
                for (RepairRequest repairRequest : repairRequestList) {
            %>
            <div class="card mb-4">
                <div class="card-body">
                    <h5 class="card-title">Фамилия: <%=repairRequest.getLastName()%>
                    </h5>
                    <h6 class="card-title">Имя: <%=repairRequest.getFirstName()%>
                    </h6>
                    <p class="card-text">Адрес: <%=repairRequest.getAddress()%>
                    </p>
                    <p>Услуги:</p>
                    <ul class="list-group">
                        <%
                            for (String service : repairRequest.getServicesList()) {
                        %>
                        <li class="list-group-item"><%=SaveRequestServlet.SERVICE_LIST.get(service)%>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
            <%
                }
            %>
        </div>
    </div>
    <%
        }
    %>
</div>
<script src="lesson26/js/bootstrap.bundle.min.js"></script>
</body>

</html>
