package models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * Class representing User of social network.
 * Contains fields for all attributes of user
 * Instance of class can be create by nested PersonBuilder class.
 */
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
