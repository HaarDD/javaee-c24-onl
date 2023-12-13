package by.teachmeskills.lesson26.dao;

import by.teachmeskills.lesson26.dto.RepairRequestDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
abstract public class RepairRequestDAO {

    private static final String INSERT_REPAIR_REQUEST = "INSERT INTO repair_request (user_id, session_id, address) VALUES (?, ?, ?)";
    private static final String SELECT_ALL_REPAIR_REQUESTS = "SELECT * FROM repair_request";
    private static final String SELECT_REPAIR_REQUEST_BY_ID = "SELECT * FROM repair_request WHERE id = ?";
    private static final String UPDATE_REPAIR_REQUEST = "UPDATE repair_request SET user_id = ?, session_id = ?, address = ? WHERE id = ?";
    private static final String DELETE_REPAIR_REQUEST = "DELETE FROM repair_request WHERE id = ?";

    private static final String SELECT_REPAIR_REQUESTS_BY_USER_EMAIL =
            "SELECT rr.* " +
                    "FROM repair_request rr " +
                    "JOIN users u ON rr.user_id = u.id " +
                    "WHERE u.email = ?";

    public static void createRepairRequest(RepairRequestDTO repairRequestDTO) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_REPAIR_REQUEST)) {

            statement.setLong(1, repairRequestDTO.getUserId());
            statement.setString(2, repairRequestDTO.getSessionId());
            statement.setString(3, repairRequestDTO.getAddress());

            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка создания заявки на ремонт: ", e);
        }
    }

    public static List<RepairRequestDTO> getAllRepairRequests() {
        List<RepairRequestDTO> repairRequests = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_REPAIR_REQUESTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RepairRequestDTO repairRequestDTO = new RepairRequestDTO();
                repairRequestDTO.setId(resultSet.getLong("id"));
                repairRequestDTO.setUserId(resultSet.getLong("user_id"));
                repairRequestDTO.setSessionId(resultSet.getString("session_id"));
                repairRequestDTO.setAddress(resultSet.getString("address"));

                repairRequests.add(repairRequestDTO);
            }

        } catch (SQLException e) {
            log.error("Ошибка получения списка заявок: ", e);
        }

        return repairRequests;
    }

    public static List<RepairRequestDTO> getRepairRequestsByUserEmail(String userEmail) {
        List<RepairRequestDTO> repairRequests = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REPAIR_REQUESTS_BY_USER_EMAIL)) {

            statement.setString(1, userEmail);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    RepairRequestDTO repairRequestDTO = new RepairRequestDTO();
                    repairRequestDTO.setId(resultSet.getLong("id"));
                    repairRequestDTO.setUserId(resultSet.getLong("user_id"));
                    repairRequestDTO.setSessionId(resultSet.getString("session_id"));
                    repairRequestDTO.setAddress(resultSet.getString("address"));

                    repairRequests.add(repairRequestDTO);
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения списка заявок пользователя: ", e);
        }

        return repairRequests;
    }

    public static RepairRequestDTO getRepairRequestById(Long repairRequestId) {
        RepairRequestDTO repairRequestDTO = new RepairRequestDTO();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REPAIR_REQUEST_BY_ID)) {

            statement.setLong(1, repairRequestId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    repairRequestDTO.setId(resultSet.getLong("id"));
                    repairRequestDTO.setUserId(resultSet.getLong("user_id"));
                    repairRequestDTO.setSessionId(resultSet.getString("session_id"));
                    repairRequestDTO.setAddress(resultSet.getString("address"));
                }
            }

        } catch (SQLException e) {
            log.error("Ошибка получения заявки по id: ", e);
        }

        return repairRequestDTO;
    }

    public static void updateRepairRequest(RepairRequestDTO repairRequestDTO) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_REPAIR_REQUEST)) {

            statement.setLong(1, repairRequestDTO.getUserId());
            statement.setString(2, repairRequestDTO.getSessionId());
            statement.setString(3, repairRequestDTO.getAddress());
            statement.setLong(4, repairRequestDTO.getId());

            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка обновления заявки: ", e);
        }
    }

    public static void deleteRepairRequest(Long repairRequestId) {
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_REPAIR_REQUEST)) {

            statement.setLong(1, repairRequestId);
            statement.executeUpdate();

        } catch (SQLException e) {
            log.error("Ошибка уделения заявки: ", e);
        }
    }
}
