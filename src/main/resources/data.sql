INSERT INTO course(id, name, created_date, last_updated_date) VALUES(10001, 'JPA practice', sysdate(), sysdate());
INSERT INTO course(id, name, created_date, last_updated_date) VALUES(10002, 'Spring practice', sysdate(), sysdate());
INSERT INTO course(id, name, created_date, last_updated_date) VALUES(10003, 'Spring Boot practice', sysdate(), sysdate());

--INSERT INTO course(id, fullname) VALUES(10001, 'JPA practice');
--INSERT INTO course(id, fullname) VALUES(10002, 'Spring practice');
--INSERT INTO course(id, fullname) VALUES(10003, 'Spring Boot practice');

--INSERT INTO course_details(id, name) VALUES(10001, 'JPA practice');
--INSERT INTO course_details(id, name) VALUES(10002, 'Spring practice');
--INSERT INTO course_details(id, name) VALUES(10003, 'Spring Boot practice');
-- SELECT * FROM COURSE WHERE name LIKE '%Updated'