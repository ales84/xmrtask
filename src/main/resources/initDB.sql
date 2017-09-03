DROP TABLE IF EXISTS resumes;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 1;

CREATE TABLE resumes
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  header           VARCHAR(255),
  url              VARCHAR(255)
);
