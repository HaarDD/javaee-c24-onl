package by.teachmeskills.lesson26;

import by.teachmeskills.lesson26.dao.RepairTypeDAO;
import by.teachmeskills.lesson26.dao.RoleDAO;
import by.teachmeskills.lesson26.dao.UserDAO;
import by.teachmeskills.lesson26.dto.RepairTypeDTO;
import by.teachmeskills.lesson26.dto.RoleDTO;
import by.teachmeskills.lesson26.dto.UserDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;

@Slf4j
@WebListener
public class RepairServiceContextListener implements ServletContextListener {

    private static final String ADMIN_ROLENAME = "Администратор";


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseManager.createDatabase();

        initializeRolesAndUser();

        initializeRepairTypes();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DatabaseManager.closeDataSource();
    }

    private void initializeRolesAndUser() {


        //создание ролей
        RoleDTO adminRole = new RoleDTO();
        adminRole.setRoleName("Администратор");
        RoleDAO.insertRole(adminRole);

        RoleDTO userRole = new RoleDTO();
        userRole.setRoleName("Пользователь");
        RoleDAO.insertRole(userRole);


        UserDTO adminUser = new UserDTO();
        adminUser.setEmail("admin");
        adminUser.setFirstname("Максим");
        adminUser.setLastname("Галицкий");
        adminUser.setRoleId(RoleDAO.getRoleByName(ADMIN_ROLENAME).getId());

        UserDAO.createUser(adminUser, "admin");


        log.info("Инициализация ролей прошла успешно");
    }

    private void initializeRepairTypes() {

        Map<String, String> repairTypesMap = Map.of(
                "R_TP", "Замена термопасты",
                "R_CL", "Чистка",
                "R_CPU", "Замена процессора",
                "R_GPU", "Замена видеочипа",
                "R_RAM", "Замена памяти",
                "R_KEYS", "Восстановление клавиатуры",
                "R_CYR", "Нанесение кириллицы",
                "R_SYS", "Установка системы");

        repairTypesMap.forEach((key, value) -> {
            RepairTypeDTO repairTypeDTO = new RepairTypeDTO();
            repairTypeDTO.setCode(key);
            repairTypeDTO.setName(value);
            RepairTypeDAO.insertRepairType(repairTypeDTO);
        });

        log.info("Инициализация типов ремонта прошла успешно");

    }

}
