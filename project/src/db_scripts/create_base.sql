CREATE TABLE auth_data(
  auth_data_id SERIAL PRIMARY KEY,
  login VARCHAR(30),
  password VARCHAR(30)
);

CREATE TABLE user_info(
  user_id SERIAL PRIMARY KEY,
  role VARCHAR(10),
  first_name VARCHAR(30),
  second_name VARCHAR(30),
  auth_data_id INTEGER NOT NULL,
  FOREIGN KEY (auth_data_id) REFERENCES auth_data
);

CREATE TABLE admin(
  admin_id SERIAL PRIMARY KEY,
  user_id INTEGER,
  FOREIGN KEY (user_id) REFERENCES user_info
);

CREATE TABLE client(
  client_id SERIAL PRIMARY KEY,
  user_id INTEGER,
  FOREIGN KEY (user_id) REFERENCES user_info
);

CREATE TABLE landscaper(
  client_id SERIAL PRIMARY KEY,
  user_id INTEGER,
  FOREIGN KEY (user_id) REFERENCES user_info
);

CREATE TABLE instruction(
    instruction_id SERIAL PRIMARY KEY,
    text text
);

CREATE TABLE plant(
    plant_id SERIAL PRIMARY KEY,
    client_id INTEGER,
    type VARCHAR(30),
    last_inspection VARCHAR(10),
    next_inspection VARCHAR(10),
    instruction_id INTEGER,
    FOREIGN KEY (client_id) REFERENCES client,
    FOREIGN KEY (instruction_id) REFERENCES instruction
);

CREATE TABLE resource(
    resource_id SERIAL PRIMARY KEY,
    plant_id INTEGER,
    type VARCHAR(30),
    FOREIGN KEY (plant_id) REFERENCES plant
);

CREATE TABLE creq(
    creq_id SERIAL PRIMARY KEY,
    status VARCHAR(15),
    type VARCHAR(10),
    plant_id INTEGER,
    admin_id INTEGER,
    client_id INTEGER,
    landscaper_id INTEGER,
    FOREIGN KEY (plant_id) REFERENCES plant,
    FOREIGN KEY (admin_id) REFERENCES admin,
    FOREIGN KEY (client_id) REFERENCES client,
    FOREIGN KEY (landscaper_id) REFERENCES landscaper
);

CREATE TABLE preq(
    preq_id SERIAL PRIMARY KEY,
    status VARCHAR(15),
    plant_id INTEGER,
    admin_id INTEGER,
    client_id INTEGER,
    landscaper_id INTEGER,
    creq_id INTEGER,
    FOREIGN KEY (plant_id) REFERENCES plant,
    FOREIGN KEY (admin_id) REFERENCES admin,
    FOREIGN KEY (client_id) REFERENCES client,
    FOREIGN KEY (landscaper_id) REFERENCES landscaper,
    FOREIGN KEY (creq_id) REFERENCES creq
);


CREATE TABLE feedback(
    feedback_id SERIAL PRIMARY KEY,
    type VARCHAR(10),
    text VARCHAR(80),
    creq_id INTEGER,
    FOREIGN KEY (creq_id) REFERENCES creq
)




