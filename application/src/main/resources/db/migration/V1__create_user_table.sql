CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  first VARCHAR(255),
  last VARCHAR(255),
  salt VARCHAR(255),
  hash VARCHAR(255),
  PRIMARY KEY (id)
);
