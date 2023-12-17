package by.teachmeskills.lesson26.dao;

import by.teachmeskills.lesson26.dto.RepairTypeDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class RepairTypeDAO {
    private static final String INSERT_REPAIR_TYPE = "INSERT INTO repair_types (code, name) VALUES (?, ?)";
    private static final String SELECT_ALL_REPAIR_TYPES = "SELECT * FROM repair_types";
    private static final String SELECT_REPAIR_TYPE_BY_ID = "SELECT * FROM repair_types WHERE id = ?";
    private static final String UPDATE_REPAIR_TYPE = "UPDATE repair_types SET code = ?, name = ? WHERE id = ?";
    private static final String DELETE_REPAIR_TYPE = "DELETE FROM repair_types WHERE id = ?";

    public static void insertRepairType(RepairTypeDTO repairTypeDTO) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_REPAIR_TYPE)) {

            statement.setString(1, repairTypeDTO.getCode());
            statement.setString(2, repairTypeDTO.getName());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка вставки типа ремонта: ", e);
        }
    }

    public static List<RepairTypeDTO> getAllRepairTypes() {
        List<RepairTypeDTO> repairTypes = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REPAIR_TYPES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RepairTypeDTO repairType = new RepairTypeDTO();
                repairType.setId(resultSet.getLong("id"));
                repairType.setCode(resultSet.getString("code"));
                repairType.setName(resultSet.getString("name"));
                repairTypes.add(repairType);
            }

        } catch (SQLException e) {
            log.error("Ошибка получения всех типов ремонта: ", e);
        }

        return repairTypes;
    }

    public static RepairTypeDTO getRepairTypeById(Long repairTypeId) {
        RepairTypeDTO repairType = new RepairTypeDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REPAIR_TYPE_BY_ID)) {

            statement.setLong(1, repairTypeId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    repairType.setId(resultSet.getLong("id"));
                    repairType.setCode(resultSet.getString("code"));
                    repairType.setName(resultSet.getString("name"));
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения типа ремонта по id: ", e);
        }

        return repairType;
    }


    public static void updateRepairType(RepairTypeDTO repairTypeDTO) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REPAIR_TYPE)) {

            statement.setString(1, repairTypeDTO.getCode());
            statement.setString(2, repairTypeDTO.getName());
            statement.setLong(3, repairTypeDTO.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка обновления типа ремонта: ", e);
        }
    }

    public static void deleteRepairType(Long repairTypeId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_REPAIR_TYPE)) {

            statement.setLong(1, repairTypeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка удаления типа ремонта: ", e);
        }
    }

    public static RepairTypeDTO getRepairTypeByCode(String code) {
        RepairTypeDTO repairType = new RepairTypeDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM repair_types WHERE code = ?")) {

            statement.setString(1, code);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    repairType.setId(resultSet.getLong("id"));
                    repairType.setCode(resultSet.getString("code"));
                    repairType.setName(resultSet.getString("name"));
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения типа ремонта по коду: ", e);
        }

        return repairType;
    }
}
