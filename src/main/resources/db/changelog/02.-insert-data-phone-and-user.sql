INSERT INTO testdb.USERS (ID, EMAIL, USERNAME, PASSWORD, CREATED, MODIFIED, LAST_LOGIN)
VALUES ('56703ce9-2071-4ad2-8070-8aec776188ed', 'eatatjoes@acme.com', 'Joe', '123456', '2022-10-05T00:00', '2022-10-05T00:00', '2022-10-05T00:00');

INSERT INTO testdb.PHONES (CITY_CODE, COUNTRY_CODE, NUMBER, USERS_ID)
VALUES ('01', '51', '999999999', '56703ce9-2071-4ad2-8070-8aec776188ed');