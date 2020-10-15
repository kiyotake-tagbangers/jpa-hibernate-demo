INSERT INTO course(id, name, created_date, last_updated_date) VALUES(10001, 'JPA practice', sysdate(), sysdate());
INSERT INTO course(id, name, created_date, last_updated_date) VALUES(10002, 'Spring practice', sysdate(), sysdate());
INSERT INTO course(id, name, created_date, last_updated_date) VALUES(10003, 'Spring Boot practice', sysdate(), sysdate());

--INSERT INTO course(id, fullname) VALUES(10003, 'Spring Boot practice');
--INSERT INTO course_details(id, name) VALUES(10001, 'JPA practice');

INSERT INTO passport(id, number) VALUES(30001,'A123456');
INSERT INTO passport(id, number) VALUES(30002,'B123456');
INSERT INTO passport(id, number) VALUES(30003,'C123456');

-- passport_id is a foreign key
-- this is the ID in the passport table primary key
INSERT INTO student(id, name, passport_id) VALUES(20001,'Yamada', 30001);
INSERT INTO student(id, name, passport_id) VALUES(20002,'Tanaka', 30002);
INSERT INTO student(id, name, passport_id) VALUES(20003,'Sato', 30003);
-- SELECT * FROM STUDENT ,PASSPORT WHERE STUDENT.PASSPORT_ID=PASSPORT.ID;
--   ID  	NAME  	PASSPORT_ID  	ID  	NUMBER
--   20001	Yamada	30001	30001	A123456
--   20002	Tanaka	30002	30002	B123456
--   20003	Sato	30003	30003	C123456

INSERT INTO review(id, rating, description) VALUES(40001,'5','Great Course');
INSERT INTO review(id, rating, description) VALUES(40002,'4','Wonderful Course');
INSERT INTO review(id, rating, description) VALUES(40003,'3 ','Awesome Course');
