CREATE USER 'Spring'@'localhost' IDENTIFIED BY 'P4554Spr1ng';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, REFERENCES ON ITMONITOR.* TO 'Spring'@'localhost';

DROP TABLE MACHINE;
DROP TABLE CPU;
DROP TABLE OS;
DROP TABLE PROGRAM;
DROP TABLE BATTERY;
DROP TABLE REMOTE_CONTROL;
DROP TABLE USER;
DROP TABLE USER_MACHINE;


CREATE TABLE MACHINE (ID VARCHAR(32),
                    NAME VARCHAR(32),
                    TYPE VARCHAR(10),
                    REGISTER_DATE VARCHAR(19),
                    LAST_TIMESTAMP VARCHAR(19),
                    SCREEN_CONSENT VARCHAR(1),
                    PRIMARY KEY (ID));

INSERT INTO MACHINE (ID, REGISTER_DATE) VALUES ('DBE2C1100314005DC830A1', '2022-08-21 00:00:01');
INSERT INTO MACHINE (ID, REGISTER_DATE) VALUES ('PKWLF018JEW2PZ', '2022-08-21 00:00:01');

CREATE TABLE CPU (MACHINE_ID VARCHAR(32),
                TIMESTAMP VARCHAR(19),
                MODEL VARCHAR(100),
                MICROARCHITECTURE VARCHAR(32),
                LOGICAL_CORES VARCHAR(3),
                PHYSICAL_CORES VARCHAR(2),
                TEMPERATURE VARCHAR(6),
                USAGE_LOAD VARCHAR(4),
                PRIMARY KEY (MACHINE_ID, TIMESTAMP),
                FOREIGN KEY (MACHINE_ID) REFERENCES MACHINE(ID)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE);

CREATE TABLE OS (MACHINE_ID VARCHAR(32),
                TIMESTAMP VARCHAR(19),
                FAMILY VARCHAR(16),
                VERSION VARCHAR(32),
                MANUFACTURER VARCHAR(16),
                USER VARCHAR(32),
                HOSTNAME VARCHAR(32),
                BITNESS VARCHAR(2),
                PRIMARY KEY (MACHINE_ID),
                FOREIGN KEY (MACHINE_ID) REFERENCES MACHINE(ID)
                     ON UPDATE CASCADE
                     ON DELETE CASCADE);

CREATE TABLE PROGRAM (MACHINE_ID VARCHAR(32),
                    TIMESTAMP VARCHAR(19),
                    NAME VARCHAR(128),
                    PRIMARY KEY (MACHINE_ID, NAME),
                    FOREIGN KEY (MACHINE_ID) REFERENCES MACHINE(ID)
                          ON UPDATE CASCADE
                          ON DELETE CASCADE);

CREATE TABLE BATTERY (MACHINE_ID VARCHAR(32),
                  TIMESTAMP VARCHAR(19),
                  AMPERAGE VARCHAR(10),
                  PLUGGED VARCHAR(10),
                  CHARGE_PERCENT VARCHAR(10),
                  DISCHARGE_TIME VARCHAR(10),
                  CHARGE_CYCLES VARCHAR(10),
                  TEMPERATURE VARCHAR (6),
                  PRIMARY KEY (MACHINE_ID, TIMESTAMP),
                  FOREIGN KEY (MACHINE_ID) REFERENCES MACHINE(ID)
                      ON UPDATE CASCADE
                      ON DELETE CASCADE);

CREATE TABLE REMOTE_CONTROL (MACHINE_ID VARCHAR(32),
                             TIMESTAMP VARCHAR(19),
                             COMMAND VARCHAR(100),
                             PRIMARY KEY (MACHINE_ID),
                             FOREIGN KEY (MACHINE_ID) REFERENCES MACHINE(ID)
                                 ON UPDATE CASCADE
                                 ON DELETE CASCADE);

CREATE TABLE USER (NAME VARCHAR(32),
                    PASSWORD VARCHAR(60),
                    REGISTER_DATE VARCHAR(19),
                    PRIMARY KEY (NAME);

CREATE TABLE USER_MACHINE (USERNAME VARCHAR(32),
                            MACHINE_ID VARCHAR(32),
                            PRIMARY KEY (USERNAME, MACHINE_ID),
                            CONSTRAINT FK_user FOREIGN KEY (USERNAME)
                                REFERENCES USER(NAME)
                                ON UPDATE CASCADE
                                ON DELETE CASCADE,
                            CONSTRAINT FK_machine FOREIGN KEY (MACHINE_ID)
                                REFERENCES MACHINE(ID)
                                ON UPDATE CASCADE
                                ON DELETE CASCADE);

INSERT INTO USER_MACHINE VALUES ('p', 'DBE2C1100314005DC830A1');
INSERT INTO USER_MACHINE VALUES ('p', 'PKWLF018JEW2PZ');

COMMIT;