package by.teachmeskills.lesson29.ocp.bad.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Client {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;

}
