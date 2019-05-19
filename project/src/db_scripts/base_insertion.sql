INSERT INTO auth_data (login, password) VALUES ('l_admin','Qwerty123');
INSERT INTO auth_data (login, password) VALUES ('l_client','Qwerty123');
INSERT INTO auth_data (login, password) VALUES ('l_landscaper','Qwerty123');
INSERT INTO auth_data (login, password) VALUES ('fox','Qwerty123');
INSERT INTO auth_data (login, password) VALUES ('test','Qwerty123');

INSERT INTO admin (user_id) VALUES (1);
INSERT INTO admin (user_id) VALUES (4);

INSERT INTO client (user_id) VALUES (2);
INSERT INTO client (user_id) VALUES (5);

INSERT INTO landscaper (user_id) VALUES (3);

INSERT INTO user_info (role, first_name, second_name, auth_data_id)
    VALUES ('admin', 'Svetlana','Sagadeeva', 1);
INSERT INTO user_info (role, first_name, second_name, auth_data_id)
    VALUES ('client', 'Svetlana','Sagadeeva', 2);
INSERT INTO user_info (role, first_name, second_name, auth_data_id)
    VALUES ('landscaper', 'Svetlana','Sagadeeva', 3);
INSERT INTO user_info (role, first_name, second_name, auth_data_id)
    VALUES ('admin', 'Arina','Fox', 4);
INSERT INTO user_info (role, first_name, second_name, auth_data_id)
    VALUES ('client', 'Test','TestTest', 5);

INSERT INTO instruction (text) VALUES ('Use soil_type_1 and wooden stick for fixation');
INSERT INTO instruction (text) VALUES ('Use soil_type_2 and lopatka');

INSERT INTO plant (client_id, type, last_inspection, next_inspection, instruction_id)
    VALUES (1, 'Blue orchid', '19/05/19', '26/05/19', 1);
INSERT INTO plant (client_id, type, last_inspection, next_inspection, instruction_id)
    VALUES (1, 'Cactus', '19/05/19', '25/05/19', 2);

INSERT INTO resource (plant_id, type) VALUES (1,'soil_type1');
INSERT INTO resource (plant_id, type) VALUES (2,'soil_type2');
INSERT INTO resource (plant_id, type) VALUES (1,'wooden stick');
INSERT INTO resource (plant_id, type) VALUES (2,'lopatka');

INSERT INTO creq (status, type, plant_id, admin_id, client_id, landscaper_id)
    VALUES ('inProgress', 'firstOne', 1, 1, 1, 1);
INSERT INTO creq (status, type, plant_id, admin_id, client_id, landscaper_id)
    VALUES ('inPurchase', 'firstOne', 2, 2, 2, 1);
INSERT INTO creq (status, type, plant_id, admin_id, client_id, landscaper_id)
    VALUES ('done', 'firstOne', 1, 1, 1, 1);
INSERT INTO creq (status, type, plant_id, admin_id, client_id, landscaper_id)
    VALUES ('inProgress', 'firstOne', 2, 2, 2, 1);
INSERT INTO creq (status, type, plant_id, admin_id, client_id, landscaper_id)
   VALUES ('don e', 'planned', 2, 2, 2, 1);


INSERT INTO feedback (type, text, creq_id) VALUES ('positive', 'Thanks, everything is fine!', 11);
INSERT INTO feedback (type, text, creq_id) VALUES ('negative', 'Landscaper was too late, work is pustponed.', 13);

INSERT INTO preq (status, plant_id, admin_id, client_id, landscaper_id, creq_id)
    VALuES ('inProgress', 1, 1, 1, 1, 10);



