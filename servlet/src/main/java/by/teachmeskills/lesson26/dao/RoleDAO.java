package by.teachmeskills.lesson26.dao;

import by.teachmeskills.lesson26.dto.RoleDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
abstract public class RoleDAO {
    private static final String INSERT_ROLE = "INSERT INTO roles (role_name) VALUES (?)";
    private static final String SELECT_ALL_ROLES = "SELECT * FROM roles";
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private static final String SELECT_ROLE_BY_NAME = "SELECT * FROM roles WHERE role_name = ?";
    private static final String UPDATE_ROLE = "UPDATE roles SET role_name = ? WHERE id = ?";
    private static final String DELETE_ROLE = "DELETE FROM roles WHERE id = ?";

    public static void insertRole(RoleDTO roleDTO) {
        String roleName = roleDTO.getRoleName();

        if (roleNameExists(roleName)) {
            log.info("Роль уже есть: " + roleName);
            return;
        }
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ROLE)) {
            statement.setString(1, roleName);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка вставки роли: ", e);
        }
    }

    private static boolean roleNameExists(String roleName) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT id FROM roles WHERE role_name = ?")) {

            statement.setString(1, roleName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            log.error("Ошибка проверки на существующую роль: ", e);
            return false;
        }
    }

    public static List<RoleDTO> getAllRoles() {
        List<RoleDTO> roles = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROLES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RoleDTO role = new RoleDTO();
                role.setId(resultSet.getLong("id"));
                role.setRoleName(resultSet.getString("role_name"));
                roles.add(role);
            }

        } catch (SQLException e) {
            log.error("Ошибка получения списка ролей:  ", e);
        }

        return roles;
    }

    public static RoleDTO getRoleByName(String roleName) {
        RoleDTO role = new RoleDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_NAME)) {

            statement.setString(1, roleName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role.setId(resultSet.getLong("id"));
                    role.setRoleName(resultSet.getString("role_name"));
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения роли по имени: ", e);
        }

        return role;
    }


    public static RoleDTO getRoleById(Long roleId) {
        RoleDTO role = new RoleDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_ID)) {

            statement.setLong(1, roleId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role.setId(resultSet.getLong("id"));
                    role.setRoleName(resultSet.getString("role_name"));
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения роли по id: ", e);
        }

        return role;
    }

    public static void updateRole(RoleDTO roleDTO) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ROLE)) {

            statement.setString(1, roleDTO.getRoleName());
            statement.setLong(2, roleDTO.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка обновления роли: ", e);
        }
    }

    public static void deleteRole(Long roleId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ROLE)) {

            statement.setLong(1, roleId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка удаления роли: ", e);
        }
    }
}

