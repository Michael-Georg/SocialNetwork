CREATE TABLE Person (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255),
  dob        DATE,
  email      VARCHAR(255) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  address    VARCHAR(255),
  telephone  VARCHAR(15),
  info       VARCHAR(255)
);

CREATE TABLE Friends(
  id_user INT NOT NULL REFERENCES  Person(id),
  id_friend INT NOT NULL REFERENCES Person(id),
  PRIMARY KEY (id_user, id_friend)
);

CREATE TABLE Messages (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  id_user INT NOT NULL ,
  id_post INT NOT NULL ,
  text    VARCHAR(255),
  FOREIGN KEY (id_user) REFERENCES Person(id)
);


INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone, info)
VALUES ('Alf', 'Alf', '1980-06-15', 'ALF@ALF.ALF', '', 'Franco square, 5/1, 10',
        '+38007654321', 'info info');
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

INSERT INTO Messages(id, id_user, text, id_post) VALUES (1, 1, 'FIRST MSG', -1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (2, 1, 'ALF MSG', -1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (3, 2, 'SECOND MSG', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (4, 3, 'THIRD MSG', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (5, 4, 'FORTH MSG', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (6, 4, 'comment 1', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (12, 4, 'comment 3', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (7, 4, 'comment 2', -2);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (11, 4, 'comment 3', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (10, 4, 'comment 3', 1);
INSERT INTO Messages(id, id_user, text, id_post) VALUES (9, 4, 'comment 3', 1);

