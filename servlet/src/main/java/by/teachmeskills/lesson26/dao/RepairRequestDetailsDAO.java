package by.teachmeskills.lesson26.dao;

import by.teachmeskills.lesson26.dto.RepairRequestDetailsDTO;
import by.teachmeskills.lesson26.services.DatabaseManager;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class RepairRequestDetailsDAO {

    private static final String SELECT_REPAIR_REQUEST_DETAILS =
            "SELECT repair_types.name, repair_request.session_id, repair_request.address, users.email, users.firstname, users.lastname " +
                    "FROM repair_request_repair_types " +
                    "INNER JOIN repair_request ON repair_request_repair_types.repair_request_id = repair_request.id " +
                    "INNER JOIN repair_types ON repair_request_repair_types.repair_type_id = repair_types.id " +
                    "INNER JOIN users ON repair_request.user_id = users.id " +
                    "INNER JOIN roles ON users.role_id = roles.id";

    public static List<RepairRequestDetailsDTO> getRepairRequestDetails() {
        List<RepairRequestDetailsDTO> repairRequestDetailsList = new ArrayList<>();

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_REPAIR_REQUEST_DETAILS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                RepairRequestDetailsDTO repairRequestDetails = new RepairRequestDetailsDTO();
                repairRequestDetails.setRepairTypeName(resultSet.getString("name"));
                repairRequestDetails.setSessionId(resultSet.getString("session_id"));
                repairRequestDetails.setAddress(resultSet.getString("address"));
                repairRequestDetails.setUserEmail(resultSet.getString("email"));
                repairRequestDetails.setUserFirstname(resultSet.getString("firstname"));
                repairRequestDetails.setUserLastname(resultSet.getString("lastname"));
                repairRequestDetailsList.add(repairRequestDetails);
            }

        } catch (SQLException e) {
            log.error("Ошибка подробной информации по запросам: ", e);
        }

        return repairRequestDetailsList;
    }

}
