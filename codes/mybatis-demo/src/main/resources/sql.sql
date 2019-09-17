CREATE DATABASE app;

CREATE TABLE IF NOT EXISTS student
(
  id text PRIMARY KEY,
  name text,
  age integer,
  birthday timestamp without time zone
);

