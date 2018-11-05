/*

For the table Students, Enrollments and Courses:
1. Insert the following information into the appropriate table(s).
   A student 'Mickey Mouse' with  NetId  'mm1' joins 'CS' department.
   He is taking 'Computer Graphics' course ( CRN 195) offered by
   Prof. 'Donald Duck'from 'CS' department.
2. Write a SQL query that returns the LastName of student, Title of course
   he/she is taking, and the Score, for all students in 'CS' department.

*/


INSERT INTO students VALUE('mm1', 'Mickey', 'Mouse', 'CS');
INSERT INTO courses  VALUE('195', 'Computer Graphics', 'CS', 'Donald Duck');
INSERT INTO enrollments(netid, crn) VALUE('mm1', '195');


SELECT	s.lastname, c.title, e.score
FROM 	students s, courses c, enrollments e
WHERE   s.netid = e.netid
		AND c.crn = e.crn
		AND s.department = 'CS';