package by.teachmeskills.lesson26.dto;

import lombok.Data;

@Data
public class RepairRequestDTO {

    private Long id;
    private Long userId;
    private String sessionId;
    private String address;
}
