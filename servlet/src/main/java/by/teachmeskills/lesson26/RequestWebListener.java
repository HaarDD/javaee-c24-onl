package by.teachmeskills.lesson26;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static by.teachmeskills.lesson26.SaveRequestServlet.SERVICE_LIST;
import static by.teachmeskills.lesson26.dto.RepairRequest.REPAIR_REQUEST_FIELDS_CYR_NAMES;

@WebListener
public class RequestWebListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("fieldsTranslator", REPAIR_REQUEST_FIELDS_CYR_NAMES);
        sce.getServletContext().setAttribute("listElementsTranslator", SERVICE_LIST);
    }
}
