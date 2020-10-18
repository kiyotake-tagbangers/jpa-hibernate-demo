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
-- ID  	NAME  	PASSPORT_ID  	ID  	NUMBER
-- 20001	Yamada	30001	30001	A123456
-- 20002	Tanaka	30002	30002	B123456
-- 20003	Sato	30003	30003	C123456

INSERT INTO review(id, rating, description, course_id) VALUES(40001,'5','Great Course', 10001);
INSERT INTO review(id, rating, description, course_id) VALUES(40002,'4','Wonderful Course', 10001);
INSERT INTO review(id, rating, description, course_id) VALUES(40003,'3 ','Awesome Course', 10003);
-- SELECT * FROM review, course WHERE course.id = review.course_id;
-- ID  	DESCRIPTION  	RATING  	COURSE_ID  	ID  	CREATED_DATE  	LAST_UPDATED_DATE  	NAME
-- 40001	Great Course	5	10001	10001	2020-10-16 00:00:00	2020-10-16 00:00:00	JPA practice
-- 40002	Wonderful Course	4	10001	10001	2020-10-16 00:00:00	2020-10-16 00:00:00	JPA practice
-- 40003	Awesome Course	3 	10003	10003	2020-10-16 00:00:00	2020-10-16 00:00:00	Spring Boot practice

INSERT INTO student_course(student_id, course_id) VALUES(20001,10001);
INSERT INTO student_course(student_id, course_id) VALUES(20002,10001);
INSERT INTO student_course(student_id, course_id) VALUES(20003,10001);
INSERT INTO student_course(student_id, course_id) VALUES(20001,10003);
-- SELECT * FROM
--   STUDENT_COURSE, STUDENT, COURSE
-- WHERE
--   STUDENT_COURSE.STUDENT_ID = STUDENT.ID
-- AND
--   STUDENT_COURSE.COURSE_ID = COURSE.ID
-- STUDENT_ID  	COURSE_ID  	ID  	NAME  	PASSPORT_ID  	ID  	CREATED_DATE  	LAST_UPDATED_DATE  	NAME
-- 20001	10001	20001	Yamada	30001	10001	2020-10-18 00:00:00	2020-10-18 00:00:00	JPA practice
-- 20002	10001	20002	Tanaka	30002	10001	2020-10-18 00:00:00	2020-10-18 00:00:00	JPA practice
-- 20003	10001	20003	Sato	30003	10001	2020-10-18 00:00:00	2020-10-18 00:00:00	JPA practice
-- 20001	10003	20001	Yamada	30001	10003	2020-10-18 00:00:00	2020-10-18 00:00:00	Spring Boot practice