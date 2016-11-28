package models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private LocalDate dob = null;
    private String email;
    private String address = null;
    private String telephone;
    private String info;
    private String password;


}
