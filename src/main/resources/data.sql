DROP TABLE IF EXISTS super_duper;

CREATE TABLE super_duper (
  id bigint PRIMARY KEY auto_increment,
  name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS item;
 
CREATE TABLE item (
  id bigint PRIMARY KEY auto_increment,
  super_duper_id bigint NOT NULL,
  name VARCHAR(50) NOT NULL,
  status VARCHAR(20) NOT NULL,
  is_deleted boolean,
  foreign key (super_duper_id) references super_duper(id)
);

DROP TABLE IF EXISTS tag;
 
CREATE TABLE tag (
  id bigint PRIMARY KEY auto_increment,
  item_id bigint NOT NULL,
  tag_desc VARCHAR(250) NOT NULL,
  foreign key (item_id) references item(id)
);

DROP TABLE IF EXISTS reminder;
 
CREATE TABLE reminder (
  id bigint PRIMARY KEY auto_increment,
  item_id bigint NOT NULL,
  desc VARCHAR(250) NOT NULL,
  datetime TIMESTAMP NOT NULL,
  foreign key (item_id) references item(id)
);

INSERT INTO super_duper (name) VALUES
  ('SuperDuper');

INSERT INTO item (super_duper_id, name, status, is_deleted) VALUES
  (select id from super_duper where name = 'SuperDuper', 'SuperItem', 'STARTED', false),
  (select id from super_duper where name = 'SuperDuper', 'DuperItem', 'STARTED', false);
  
INSERT INTO tag (item_id, tag_desc) VALUES
  (select id from item where name = 'SuperItem', 'Spring Boot App'),
  (select id from item where name = 'SuperItem', 'Java'),
  (select id from item where name = 'SuperItem', 'H2 DB'),
  (select id from item where name = 'DuperItem', 'DeltA'),
  (select id from item where name = 'DuperItem', 'Solution');
  
INSERT INTO reminder (item_id, desc, datetime) VALUES
  (select id from item where name = 'SuperItem', 'Code', '2019-08-22 22:32:00'),
  (select id from item where name = 'SuperItem', 'Test', '2019-08-22 23:32:00'),
  (select id from item where name = 'DuperItem', 'Run', '2019-08-23 22:15:15'),
  (select id from item where name = 'DuperItem', 'Push', '2019-08-23 23:40:15'),
  (select id from item where name = 'DuperItem', 'Commit', '2019-08-24 10:32:39');