/*

For the table Students, Enrollments, Courses and Departments, write a SQL query that
returns Title of each course and the number of students whose score in the course is
higher than the average score for all students taking the course.

*/

SELECT	R2.c1, Ifnull(R1.c2, 0)
FROM  	(SELECT C.title AS C1, Count (*) AS C2
		 FROM   students S, enrollments E, courses C
		 WHERE	S.netid =  E.netid AND E.crn = C.crn
				AND E.score >	(SELECT Avg(E2.score)
					     	  	 FROM   enrollments E2
					     	  	 WHERE  E2.crn = E.crn)
		GROUP BY C.title) AS R1
		RIGHT JOIN(SELECT title AS C1 FROM courses) AS R2
		ON R1.c1 = R2.c1;