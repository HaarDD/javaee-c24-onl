package by.teachmeskills.lesson26.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RepairRequest {

    private String requestSessionId;

    private String firstName;

    private String lastName;

    private String Address;

    private List<String> servicesList;

}
