CREATE TABLE Person (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255),
  dob        DATE,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  address    VARCHAR(255),
  telephone  VARCHAR(15)
);

CREATE TABLE Friends(
  id_user INT NOT NULL REFERENCES  Person(id),
  id_friend INT NOT NULL REFERENCES Person(id),
  PRIMARY KEY (id_user, id_friend)
);

INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('Alf', 'Alf', '1980-06-15', 'ALF', '', 'Franco square, 5/1, 10',
        '+38007654321');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('John', 'Eglesias', '1980-06-15', 'JohnEglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('Pit', 'Eglesias', '1980-06-15', 'Pit_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('Aisha', 'Eglesias', '1980-06-15', 'Aisha_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');

INSERT INTO Friends(id_user, id_friend) VALUES ('1', '2');
INSERT INTO Friends(id_user, id_friend) VALUES ('1', '3');
INSERT INTO Friends(id_user, id_friend) VALUES ('1', '4');
INSERT INTO Friends(id_user, id_friend) VALUES ('2', '1');