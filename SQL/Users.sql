DROP TABLE IF EXISTS users;
CREATE TABLE users(login text, pwd text, ROLE text, token text, nb int); 
INSERT INTO users VALUES ('user','user',NULL,NULL,0);
INSERT INTO users VALUES ('admin','admin',NULL,NULL,0);