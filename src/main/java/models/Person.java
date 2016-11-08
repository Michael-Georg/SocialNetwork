package models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class Person {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final Date dob;
    private final String email;
    private final String password;
    private final String address;
    private final String telephone;
}
