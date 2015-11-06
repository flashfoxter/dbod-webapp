-- Generated by Oracle SQL Developer Data Modeler 4.0.1.836
--   at:        2015-11-05 15:23:36 CET
--   site:      Oracle Database 11g
--   type:      Oracle Database 11g




CREATE USER DBONDEMAND IDENTIFIED BY ACCOUNT UNLOCK ;

CREATE TABLE DBONDEMAND.DOD_COMMAND_DEFINITION
  (
    COMMAND_NAME VARCHAR2 (64 BYTE) NOT NULL ,
    TYPE         VARCHAR2 (64 BYTE) NOT NULL ,
    EXEC         VARCHAR2 (2048 BYTE)
  )
  TABLESPACE DATA01 LOGGING ;
CREATE UNIQUE INDEX DBONDEMAND.PK_DOD_COMMAND_DEFINITION ON DBONDEMAND.DOD_COMMAND_DEFINITION
  (
    COMMAND_NAME ASC , TYPE ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.DOD_COMMAND_DEFINITION ADD CONSTRAINT PK_DOD_COMMAND_DEFINITION PRIMARY KEY ( COMMAND_NAME, TYPE ) USING INDEX DBONDEMAND.PK_DOD_COMMAND_DEFINITION ;

CREATE TABLE DBONDEMAND.DOD_COMMAND_PARAMS
  (
    USERNAME      VARCHAR2 (32 BYTE) NOT NULL ,
    DB_NAME       VARCHAR2 (128 BYTE) NOT NULL ,
    COMMAND_NAME  VARCHAR2 (64 BYTE) NOT NULL ,
    TYPE          VARCHAR2 (64 BYTE) NOT NULL ,
    CREATION_DATE DATE NOT NULL ,
    NAME          VARCHAR2 (64 BYTE) NOT NULL ,
    VALUE CLOB
  )
  TABLESPACE DATA01 LOGGING LOB
  (
    VALUE
  )
  STORE AS
  (
    TABLESPACE DATA01 STORAGE ( PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 BUFFER_POOL DEFAULT ) CHUNK 8192 RETENTION ENABLE STORAGE IN ROW NOCACHE LOGGING
  ) ;
CREATE UNIQUE INDEX DBONDEMAND.PK_DOD_COMMAND_PARAMS ON DBONDEMAND.DOD_COMMAND_PARAMS
  (
    USERNAME ASC , DB_NAME ASC , COMMAND_NAME ASC , TYPE ASC , CREATION_DATE ASC , NAME ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.DOD_COMMAND_PARAMS ADD CONSTRAINT PK_DOD_COMMAND_PARAMS PRIMARY KEY ( USERNAME, DB_NAME, COMMAND_NAME, TYPE, CREATION_DATE, NAME ) USING INDEX DBONDEMAND.PK_DOD_COMMAND_PARAMS ;

CREATE TABLE DBONDEMAND.DOD_INSTANCES
  (
    USERNAME       VARCHAR2 (32 BYTE) NOT NULL ,
    DB_NAME        VARCHAR2 (128 BYTE) NOT NULL ,
    E_GROUP        VARCHAR2 (256 BYTE) ,
    CATEGORY       VARCHAR2 (32 BYTE) NOT NULL ,
    CREATION_DATE  DATE NOT NULL ,
    EXPIRY_DATE    DATE ,
    DB_TYPE        VARCHAR2 (32 BYTE) NOT NULL ,
    DB_SIZE        NUMBER (*,0) NOT NULL ,
    NO_CONNECTIONS NUMBER (*,0) ,
    PROJECT        VARCHAR2 (128 BYTE) ,
    DESCRIPTION    VARCHAR2 (1024 BYTE) ,
    STATE          VARCHAR2 (32 BYTE) ,
    STATUS         CHAR (1 BYTE) ,
    VERSION        VARCHAR2 (128 BYTE) ,
    MASTER         VARCHAR2 (32 BYTE) ,
    SLAVE          VARCHAR2 (32 BYTE) ,
    HOST           VARCHAR2 (128 BYTE) NOT NULL
  )
  TABLESPACE DATA01 LOGGING ;
CREATE UNIQUE INDEX DBONDEMAND.PK_DOD_INSTANCES ON DBONDEMAND.DOD_INSTANCES
  (
    USERNAME ASC , DB_NAME ASC
  )
  TABLESPACE DATA01 LOGGING ;
CREATE UNIQUE INDEX DBONDEMAND.UNIQUE_DB_NAME ON DBONDEMAND.DOD_INSTANCES
  (
    DB_NAME ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.DOD_INSTANCES ADD CONSTRAINT PK_DOD_INSTANCES PRIMARY KEY ( USERNAME, DB_NAME ) USING INDEX DBONDEMAND.PK_DOD_INSTANCES ;
  ALTER TABLE DBONDEMAND.DOD_INSTANCES ADD CONSTRAINT UNIQUE_DB_NAME UNIQUE ( DB_NAME ) USING INDEX DBONDEMAND.UNIQUE_DB_NAME ;

CREATE TABLE DBONDEMAND.DOD_INSTANCE_CHANGES
  (
    USERNAME    VARCHAR2 (32 BYTE) NOT NULL ,
    DB_NAME     VARCHAR2 (128 BYTE) NOT NULL ,
    ATTRIBUTE   VARCHAR2 (32 BYTE) NOT NULL ,
    CHANGE_DATE DATE NOT NULL ,
    REQUESTER   VARCHAR2 (32 BYTE) NOT NULL ,
    OLD_VALUE   VARCHAR2 (1024 BYTE) ,
    NEW_VALUE   VARCHAR2 (1024 BYTE)
  )
  TABLESPACE DATA01 LOGGING ;
CREATE UNIQUE INDEX DBONDEMAND.PK_INSTANCE_CHANGES ON DBONDEMAND.DOD_INSTANCE_CHANGES
  (
    USERNAME ASC , DB_NAME ASC ,
    ATTRIBUTE ASC , CHANGE_DATE ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.DOD_INSTANCE_CHANGES ADD CONSTRAINT PK_INSTANCE_CHANGES PRIMARY KEY ( USERNAME, DB_NAME, ATTRIBUTE, CHANGE_DATE ) USING INDEX DBONDEMAND.PK_INSTANCE_CHANGES ;

CREATE TABLE DBONDEMAND.DOD_JOBS
  (
    USERNAME        VARCHAR2 (32 BYTE) NOT NULL ,
    DB_NAME         VARCHAR2 (128 BYTE) NOT NULL ,
    COMMAND_NAME    VARCHAR2 (64 BYTE) NOT NULL ,
    TYPE            VARCHAR2 (64 BYTE) NOT NULL ,
    CREATION_DATE   DATE NOT NULL ,
    COMPLETION_DATE DATE ,
    REQUESTER       VARCHAR2 (32 BYTE) NOT NULL ,
    ADMIN_ACTION    NUMBER (*,0) NOT NULL ,
    STATE           VARCHAR2 (32 BYTE) NOT NULL ,
    LOG CLOB ,
    RESULT     VARCHAR2 (2048 BYTE) ,
    EMAIL_SENT DATE
  )
  TABLESPACE DATA01 LOGGING LOB
  (
    LOG
  )
  STORE AS
  (
    TABLESPACE DATA01 STORAGE ( PCTINCREASE 0 MINEXTENTS 1 MAXEXTENTS UNLIMITED FREELISTS 1 BUFFER_POOL DEFAULT ) CHUNK 8192 RETENTION ENABLE STORAGE IN ROW NOCACHE LOGGING
  ) ;
CREATE UNIQUE INDEX DBONDEMAND.PK_DOD_JOBS ON DBONDEMAND.DOD_JOBS
  (
    USERNAME ASC , DB_NAME ASC , COMMAND_NAME ASC , TYPE ASC , CREATION_DATE ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.DOD_JOBS ADD CONSTRAINT PK_DOD_JOBS PRIMARY KEY ( USERNAME, DB_NAME, COMMAND_NAME, TYPE, CREATION_DATE ) USING INDEX DBONDEMAND.PK_DOD_JOBS ;

CREATE TABLE DBONDEMAND.DOD_UPGRADES
  (
    DB_TYPE      VARCHAR2 (32 BYTE) NOT NULL ,
    CATEGORY     VARCHAR2 (32 BYTE) NOT NULL ,
    VERSION_FROM VARCHAR2 (128 BYTE) NOT NULL ,
    VERSION_TO   VARCHAR2 (128 BYTE) NOT NULL
  )
  TABLESPACE DATA01 LOGGING ;
CREATE UNIQUE INDEX DBONDEMAND.PK_DOD_UPGRADES ON DBONDEMAND.DOD_UPGRADES
  (
    DB_TYPE ASC , CATEGORY ASC , VERSION_FROM ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.DOD_UPGRADES ADD CONSTRAINT PK_DOD_UPGRADES PRIMARY KEY ( DB_TYPE, CATEGORY, VERSION_FROM ) USING INDEX DBONDEMAND.PK_DOD_UPGRADES ;

CREATE TABLE DBONDEMAND.STATS_MONTHLY_INSTANCES
  (
    MONTHLY_DATE      DATE NOT NULL ,
    NUM_INSTANCES     NUMBER DEFAULT 0 NOT NULL ,
    AGG_NUM_INSTANCES NUMBER DEFAULT 0 NOT NULL
  )
  TABLESPACE DATA01 LOGGING ;
CREATE UNIQUE INDEX DBONDEMAND.PK_STATS_MONTHLY_INSTANCES ON DBONDEMAND.STATS_MONTHLY_INSTANCES
  (
    MONTHLY_DATE ASC
  )
  TABLESPACE DATA01 LOGGING ;
  ALTER TABLE DBONDEMAND.STATS_MONTHLY_INSTANCES ADD CONSTRAINT PK_STATS_MONTHLY_INSTANCES PRIMARY KEY ( MONTHLY_DATE ) USING INDEX DBONDEMAND.PK_STATS_MONTHLY_INSTANCES ;

ALTER TABLE DBONDEMAND.DOD_INSTANCE_CHANGES ADD CONSTRAINT DOD_INSTANCES_CHANGES FOREIGN KEY ( USERNAME, DB_NAME ) REFERENCES DBONDEMAND.DOD_INSTANCES ( USERNAME, DB_NAME ) ON
DELETE CASCADE NOT DEFERRABLE ;

ALTER TABLE DBONDEMAND.DOD_JOBS ADD CONSTRAINT DOD_JOBS_DEFINITIONS FOREIGN KEY ( COMMAND_NAME, TYPE ) REFERENCES DBONDEMAND.DOD_COMMAND_DEFINITION ( COMMAND_NAME, TYPE ) ON
DELETE CASCADE NOT DEFERRABLE ;

ALTER TABLE DBONDEMAND.DOD_JOBS ADD CONSTRAINT DOD_JOBS_INSTANCES FOREIGN KEY ( USERNAME, DB_NAME ) REFERENCES DBONDEMAND.DOD_INSTANCES ( USERNAME, DB_NAME ) ON
DELETE CASCADE NOT DEFERRABLE ;

ALTER TABLE DBONDEMAND.DOD_COMMAND_PARAMS ADD CONSTRAINT DOD_PARAMS_JOBS FOREIGN KEY ( USERNAME, DB_NAME, COMMAND_NAME, TYPE, CREATION_DATE ) REFERENCES DBONDEMAND.DOD_JOBS ( USERNAME, DB_NAME, COMMAND_NAME, TYPE, CREATION_DATE ) ON
DELETE CASCADE NOT DEFERRABLE ;

CREATE OR REPLACE TRIGGER DBONDEMAND.DOD_INSTANCES_DELETE 
    AFTER UPDATE OF STATUS ON DBONDEMAND.DOD_INSTANCES 
    FOR EACH ROW 
DECLARE
    message VARCHAR2 (1024);
BEGIN
    IF :NEW.status = '0'
    THEN
        message := '<html>
                        <body>
                            <p>
                                Instance <b>' || :NEW.db_name || '</b> has been removed from FIM, or has expired, and has been marked for deletion.
                            </p>
                            <p>
                                The database will still be running until manually stopped. Please take the necessary actions to free the allocated resources
                                as documented in the corresponding <a href="https://twiki.cern.ch/twiki/bin/viewauth/DB/Private/DBOnDemandDeletion">TWiki article</a>.
                            </p>
                        </body>
                    </html>';

        UTL_MAIL.send(sender => 'dbondemand-admin@cern.ch',
            recipients => 'dbondemand-admin@cern.ch',
            subject => 'DBOD: INFO: "' || :NEW.db_name || '" marked for deletion',
            message => message,
            mime_type => 'text/html');
    END IF;
END; 
/



CREATE OR REPLACE TRIGGER DBONDEMAND.DOD_INSTANCES_UPDATE_USERNAME 
    AFTER UPDATE OF DB_NAME, USERNAME ON DBONDEMAND.DOD_INSTANCES 
    FOR EACH ROW 
BEGIN
    UPDATE dod_jobs
        SET username = :NEW.username, db_name = :NEW.db_name
        WHERE username = :OLD.username AND db_name = :OLD.db_name;
    UPDATE dod_instance_changes
        SET username = :NEW.username, db_name = :NEW.db_name
        WHERE username = :OLD.username AND db_name = :OLD.db_name;
END; 
/



CREATE OR REPLACE TRIGGER DBONDEMAND.DOD_JOBS_INSERT 
    BEFORE INSERT ON DBONDEMAND.DOD_JOBS 
    FOR EACH ROW 
DECLARE
    pending INTEGER;
BEGIN
    SELECT COUNT(*) INTO pending
    FROM dod_instances
    WHERE username = :NEW.username
        AND db_name = :NEW.db_name
        AND (state = 'JOB_PENDING' OR state = 'AWAITING_APPROVAL' OR state = 'MAINTENANCE');

    IF pending > 0 AND :NEW.admin_action = 0
    THEN
        raise_application_error(-20000,'INSTANCE PENDING JOB, AWAITING APPROVAL OR UNDER MAINTENANCE');
    END IF;
END; 
/



CREATE OR REPLACE TRIGGER DBONDEMAND.DOD_JOBS_MONITOR_FAIL 
    BEFORE UPDATE OF STATE ON DBONDEMAND.DOD_JOBS 
    FOR EACH ROW 
DECLARE
    message VARCHAR2 (1024);
    max_attachment_size Number:= 32767;
    attachment VARCHAR2(32767);
    attachment_size Number ;
BEGIN
    IF :NEW.state = 'FINISHED_FAIL' OR :NEW.state = 'FINISHED_WARNING'
    THEN
        message := '<html>
                        <body>
                            The execution of the following job has failed or finished with errors:
                            <ul>
                                <li><b>Username</b>: ' || :NEW.username || '</li>
                                <li><b>DB Name</b>: ' || :NEW.db_name || '</li>
                                <li><b>Command name</b>: ' || :NEW.command_name || '</li>
                                <li><b>Type</b>: ' || :NEW.type || '</li>
                                <li><b>Creation date</b>: ' || TO_CHAR(:NEW.creation_date,'DD/MM/YYYY HH24:MI:SS') || '</li>
                                <li><b>Completion date</b>: ' || TO_CHAR(:NEW.completion_date,'DD/MM/YYYY HH24:MI:SS') || '</li>
                                <li><b>Requester</b>: ' || :NEW.requester || '</li>
                                <li><b>Admin action</b>: ' || :NEW.admin_action || '</li>
                                <li><b>State</b>: ' || :NEW.state || '</li>
                            </ul>
                        </body>
                    </html>';
        attachment_size := LENGTH(:NEW.log); 
        IF attachment_size > max_attachment_size
        THEN attachment := '[...] ' || DBMS_LOB.SUBSTR(:NEW.log,max_attachment_size-6,attachment_size-max_attachment_size+6+1);
        ELSE attachment := CAST(:NEW.log AS VARCHAR2);
        END IF;
        UTL_MAIL.send_ATTACH_VARCHAR2(sender => 'dbondemand-admin@cern.ch',
            recipients => 'dbondemand-admin@cern.ch',
            subject => 'DBOD: CRITICAL: Failed job on "' || :NEW.db_name || '"',
            message => message,
            mime_type => 'text/html',
            attachment => attachment);

        :NEW.email_sent := sysdate;
    END IF;
END; 
/



CREATE OR REPLACE TRIGGER DBONDEMAND.DOD_JOBS_UPDATE_USERNAME 
    AFTER UPDATE OF DB_NAME, USERNAME ON DBONDEMAND.DOD_JOBS 
    FOR EACH ROW 
BEGIN
    UPDATE dod_command_params
        SET username = :NEW.username, db_name = :NEW.db_name
        WHERE username = :OLD.username AND db_name = :OLD.db_name;
END; 
/



CREATE OR REPLACE TRIGGER DBONDEMAND.DOD_UPGRADE_PERFORMED 
    AFTER UPDATE OF STATE ON DBONDEMAND.DOD_JOBS 
    FOR EACH ROW 
DECLARE
    message VARCHAR2 (1024);
BEGIN
    IF :NEW.COMMAND_NAME = 'UPGRADE' AND :NEW.state = 'FINISHED_OK'
    THEN
        message := '<html>
                        <body>
                            Instance <b>' || :NEW.db_name || '</b> has been successfully upgraded to the latest version!
                        </body>
                    </html>';

        UTL_MAIL.send(sender => 'dbondemand-admin@cern.ch',
            recipients => 'dbondemand-admin@cern.ch',
            subject => 'DBOD: INFO: "' || :NEW.db_name || '" has been upgraded',
            message => message,
            mime_type => 'text/html');
    END IF;
END; 
/




-- Oracle SQL Developer Data Modeler Summary Report: 
-- 
-- CREATE TABLE                             7
-- CREATE INDEX                             8
-- ALTER TABLE                             12
-- CREATE VIEW                              0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           6
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              1
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
