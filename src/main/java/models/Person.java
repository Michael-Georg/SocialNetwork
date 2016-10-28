package models;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@Builder
public class Person {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final LocalDate dob;
    private final String email;
//    private final String password;
    private final String address;
    private final String telephone;
}
