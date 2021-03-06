CREATE TABLE Person (
  id         INT PRIMARY KEY AUTO_INCREMENT,
  first_name VARCHAR(20) NOT NULL,
  last_name  VARCHAR(20),
  dob        DATE,
  email      VARCHAR(30) NOT NULL UNIQUE,
  password   VARCHAR(255) NOT NULL,
  address    VARCHAR(255),
  telephone  VARCHAR(20),
  info       VARCHAR(255)
);

CREATE TABLE Relation (
  id_user INT NOT NULL REFERENCES  Person(id),
  id_friend INT NOT NULL REFERENCES Person(id),
  status INT NOT NULL,
  PRIMARY KEY (id_user, id_friend)
);

CREATE TABLE Messages (
  id      INT PRIMARY KEY AUTO_INCREMENT,
  id_user INT NOT NULL ,
  id_post INT NOT NULL ,
  text    VARCHAR(255),
  publish_time TIMESTAMP NOT NULL,
  FOREIGN KEY (id_user) REFERENCES Person(id)
);


INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone, info)
VALUES ('Alf', 'Alf', '1980-06-15', 'ALF@ALF.ALF', '1', 'Franco square, 5/1, 10',
        '+38007654321', 'Snowboarding');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('John', 'Eglesias', '1980-06-15', 'JohnEglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('Pit', 'Eglesias', '1980-06-15', 'Pit_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone)
VALUES ('Aisha', 'Eglesias', '1980-06-15', 'Aisha_Eglesias@mail.es', 'qwerty', 'Franco square, 5/1, 10',
        '+38007654321');
INSERT INTO Person (first_name, last_name, dob, email, password, address, telephone, info)
VALUES ('Mike', 'Georg', '2000-01-01', 'Mike@mail.ru', '1', 'St.Petersburg, Vladimirskaya str., 22, 1',
        '640-26-69', 'Argumenta ponderantur, non numerantur');

INSERT INTO Relation (id_user, id_friend, status) VALUES ('1', '2', '2');
INSERT INTO Relation (id_user, id_friend, status) VALUES ('1', '3', '1');
INSERT INTO Relation (id_user, id_friend, status) VALUES ('3', '1', '1');
INSERT INTO Relation (id_user, id_friend, status) VALUES ('2', '1', '2');
INSERT INTO Relation (id_user, id_friend, status) VALUES ('1', '4', '2');
INSERT INTO Relation (id_user, id_friend, status) VALUES ('2', '4', '2');

INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (1, 'Msg 1', -1, '2016-10-01 13:01:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (1, 'Msg 2', -1, '2016-10-03 14:21:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (2, 'Msg 3', -1, '2016-10-03 14:22:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (2, 'Msg 4', -1, '2016-10-04 03:00:00');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 5', 2, '2016-11-05 23:59:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 6', 1, '2016-11-06 11:11:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 7', 2, '2016-11-06 13:05:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 8', -1, '2016-11-11 19:43:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 9', 3, '2016-11-30 08:01:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 10', 4, '2016-12-01 01:03:36');
INSERT INTO Messages(id_user, text, id_post, publish_time) VALUES (4, 'Msg 11', 4, '2016-10-03 20:17:00');