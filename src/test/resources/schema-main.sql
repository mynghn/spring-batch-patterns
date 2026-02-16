DROP TABLE IF EXISTS PUBLIC.COPIED_FROM_EXTERNAL_SOURCE;

CREATE TABLE PUBLIC.COPIED_FROM_EXTERNAL_SOURCE
(
    SRC_ID integer PRIMARY KEY,
    COL1 varchar(400),
    COL2 varchar(400),
    COL3 varchar(400),
    SRC_CREATED_AT timestamp not null,
    BASE_DATE date not null,
    COPIED_AT timestamp not null
);

