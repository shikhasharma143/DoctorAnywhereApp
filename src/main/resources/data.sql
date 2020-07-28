insert into PATIENTS (FIRST_NAME, LAST_NAME,  gender, phone, email) values ('PatientOne', 'LastName', 'F','1234567890', 'test@test.com');
insert into PATIENTS (FIRST_NAME, LAST_NAME,  gender, phone, email) values ('PatientTwo', 'LastName', 'M','1234567890', 'test2@test.com');
insert into ADDRESS (address_line1, city,postal_code, country_code, state, patient_id, is_default) values ('Test address','Delhi', '110059','IN', 'Delhi', 1, TRUE);
insert into ADDRESS (address_line1, city,postal_code, country_code, state, patient_id, is_default) values ('Test second address','Delhi', '110059','IN', 'Delhi', 1, FALSE);
insert into ADDRESS (address_line1, city,postal_code, country_code, state, patient_id, is_default) values ('Test Third address','Delhi', '110059','IN', 'Delhi', 1, FALSE);
insert into ADDRESS (address_line1, city,postal_code, country_code, state, patient_id, is_default) values ('Test first address','Delhi', '110059','IN', 'Delhi', 2, TRUE);

