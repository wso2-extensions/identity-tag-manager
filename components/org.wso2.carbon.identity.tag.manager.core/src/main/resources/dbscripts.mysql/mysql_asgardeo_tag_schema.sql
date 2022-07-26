CREATE TABLE IF NOT EXISTS IDN_TAG (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    UUID VARCHAR (255) NOT NULL,
    NAME VARCHAR (255) NOT NULL,
    DESCRIPTION VARCHAR (1000),
    IS_PUBLICLY_VISIBLE VARCHAR (1) NOT NULL,
    TYPE VARCHAR (255) NOT NULL,
    TENANT_UUID VARCHAR (255) NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE KEY (UUID),
    UNIQUE KEY (NAME,TYPE,TENANT_UUID)
)ENGINE INNODB;

CREATE INDEX IDN_TAG_IND_BY_TENANT_TAG_TYPE ON IDN_TAG(TENANT_UUID,TYPE);
CREATE INDEX IDN_TAG_IND_BY_TENANT ON IDN_TAG(TENANT_UUID);


CREATE TABLE IF NOT EXISTS IDN_TAG_ASCS (
    ID INTEGER NOT NULL AUTO_INCREMENT,
    RESOURCE_UUID VARCHAR (255) NOT NULL,
    TAG_UUID VARCHAR (255) NOT NULL,
    TENANT_UUID VARCHAR (255) NOT NULL,
    PRIMARY KEY (ID),
    UNIQUE KEY (TAG_UUID, RESOURCE_UUID),
    CONSTRAINT FK_IDN_TAG_ASCS FOREIGN KEY (TAG_UUID) REFERENCES IDN_TAG(UUID) ON DELETE CASCADE
)ENGINE INNODB;

CREATE INDEX IDN_TAG_ASCS_IND_BY_TENANT_TAG_UUID ON IDN_TAG_ASCS(TENANT_UUID,TAG_UUID);
CREATE INDEX IDN_TAG_ASCS_IND_BY_TENANT_RESOURCE_UUID ON IDN_TAG_ASCS(TENANT_UUID,RESOURCE_UUID);
