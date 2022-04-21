CREATE TABLE IF NOT EXISTS user (
  id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name varchar(32) DEFAULT NULL,
  email varchar(32) NOT NULL,
  password varchar(32) DEFAULT NULL,
  UNIQUE (email)
);

-- ALTER TABLE user ADD CONSTRAINT unique_email UNIQUE (email);

CREATE TABLE IF NOT EXISTS todo (
  id int AUTO_INCREMENT NOT NULL PRIMARY KEY,
  user_id int NOT NULL,
  title varchar(512) DEFAULT NULL,
  done boolean DEFAULT FALSE NOT NULL,
  priority tinyint NOT NULL,
  due_date date DEFAULT NULL,
  FOREIGN KEY (aut_id) REFERENCES user(id)
);

-- alter table todo add constraint user_fk foreign key (user_id) references user(id);