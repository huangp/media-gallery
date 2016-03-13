CREATE TABLE MediaFile (
    id                IDENTITY PRIMARY KEY,
    creationDate      TIMESTAMP    NOT NULL,
    lastChanged       TIMESTAMP    NOT NULL,
    version           BIGINT      NOT NULL,
    filePath          VARCHAR(256) NOT NULL,
    fileType          VARCHAR(8)   NOT NULL,
    mediaCreationDate TIMESTAMP    NOT NULL,

    UNIQUE (filePath)
);

CREATE TABLE User (
    id       IDENTITY PRIMARY KEY,
    username VARCHAR(128) NOT NULL,
    UNIQUE (username)
)