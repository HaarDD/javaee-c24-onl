package by.teachmeskills.lesson26.dto;

import lombok.Data;

@Data
public class RepairRequestDetailsDTO {
    private String repairTypeName;
    private String sessionId;
    private String address;
    private String userEmail;
    private String userFirstname;
    private String userLastname;
}
