package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = "info")
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

    @java.beans.ConstructorProperties({"id", "firstName", "lastName", "dob", "email", "address", "telephone", "info", "password"})
    Person(Integer id, String firstName, String lastName, LocalDate dob, String email, String address, String telephone, String info, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.telephone = telephone;
        this.info = info;
        this.password = password;
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    public static class PersonBuilder {
        private Integer id;
        private String firstName;
        private String lastName;
        private LocalDate dob;
        private String email;
        private String address;
        private String telephone;
        private String info;
        private String password;

        PersonBuilder() {
        }

        public Person.PersonBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public Person.PersonBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Person.PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Person.PersonBuilder dob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public Person.PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Person.PersonBuilder address(String address) {
            this.address = address;
            return this;
        }

        public Person.PersonBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public Person.PersonBuilder info(String info) {
            this.info = info;
            return this;
        }

        public Person.PersonBuilder password(String password) {
            this.password = password;
            return this;
        }

        public Person build() {
            return new Person(id, firstName, lastName, dob, email, address, telephone, info, password);
        }

        public String toString() {
            return "models.Person.PersonBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", dob=" + this.dob + ", email=" + this.email + ", address=" + this.address + ", telephone=" + this.telephone + ", info=" + this.info + ", password=" + this.password + ")";
        }
    }
}
