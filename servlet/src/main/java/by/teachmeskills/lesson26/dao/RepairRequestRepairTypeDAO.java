package by.teachmeskills.lesson26.dao;

import by.teachmeskills.lesson26.dto.RepairRequestRepairTypeDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
abstract public class RepairRequestRepairTypeDAO {
    private static final String INSERT_REPAIR_REQUEST_REPAIR_TYPE = "INSERT INTO repair_request_repair_types (repair_request_id, repair_type_id) VALUES (?, ?)";
    private static final String SELECT_REPAIR_REQUEST_REPAIR_TYPE_BY_IDS = "SELECT * FROM repair_request_repair_types WHERE repair_request_id = ? AND repair_type_id = ?";
    private static final String DELETE_REPAIR_REQUEST_REPAIR_TYPE_BY_IDS = "DELETE FROM repair_request_repair_types WHERE repair_request_id = ? AND repair_type_id = ?";
    private static final String SELECT_ALL_REPAIR_REQUEST_REPAIR_TYPES = "SELECT * FROM repair_request_repair_types";

    public static void insertRepairRequestRepairType(RepairRequestRepairTypeDTO repairRequestRepairTypeDTO) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_REPAIR_REQUEST_REPAIR_TYPE)) {

            statement.setLong(1, repairRequestRepairTypeDTO.getRepairRequestId());
            statement.setLong(2, repairRequestRepairTypeDTO.getRepairTypeId());
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка вставки связи многие-ко-многим (типы ремонта - заявки)", e);
        }
    }

    public static RepairRequestRepairTypeDTO getRepairRequestRepairTypeByIds(Long repairRequestId, Long repairTypeId) {
        RepairRequestRepairTypeDTO repairRequestRepairTypeDTO = new RepairRequestRepairTypeDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REPAIR_REQUEST_REPAIR_TYPE_BY_IDS)) {

            statement.setLong(1, repairRequestId);
            statement.setLong(2, repairTypeId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    repairRequestRepairTypeDTO.setRepairRequestId(resultSet.getLong("repair_request_id"));
                    repairRequestRepairTypeDTO.setRepairTypeId(resultSet.getLong("repair_type_id"));
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения заявка на ремонт id id: ", e);
        }

        return repairRequestRepairTypeDTO;
    }

    public static void deleteRepairRequestRepairTypeByIds(Long repairRequestId, Long repairTypeId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_REPAIR_REQUEST_REPAIR_TYPE_BY_IDS)) {

            statement.setLong(1, repairRequestId);
            statement.setLong(2, repairTypeId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка получения заявок на ремонт", e);
        }
    }

    public static List<RepairRequestRepairTypeDTO> getAllRepairRequestRepairTypes() {
        List<RepairRequestRepairTypeDTO> repairRequestRepairTypes = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REPAIR_REQUEST_REPAIR_TYPES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RepairRequestRepairTypeDTO repairRequestRepairTypeDTO = new RepairRequestRepairTypeDTO();
                repairRequestRepairTypeDTO.setRepairRequestId(resultSet.getLong("repair_request_id"));
                repairRequestRepairTypeDTO.setRepairTypeId(resultSet.getLong("repair_type_id"));
                repairRequestRepairTypes.add(repairRequestRepairTypeDTO);
            }

        } catch (SQLException e) {
            log.error("Ошибка получения всех заявок: ", e);
        }

        return repairRequestRepairTypes;
    }
}
