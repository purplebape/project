CREATE SEQUENCE link_id_seq;

CREATE TABLE IF NOT EXISTS link
(
    id  BIGINT PRIMARY KEY DEFAULT nextval('link_id_seq'),
    url TEXT UNIQUE NOT NULL
);