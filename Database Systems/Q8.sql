/*

For the table Students, Enrollments and Courses, write a SQL query that returns
the NetId of students who received highest score for a non-departmental
(a department other than his own) course among all students who attended that course.

*/

SELECT 	DISTINCT S.netid
FROM   	students S, enrollments E, courses C
WHERE  	S.netid = E.netid
		AND E.crn = C.crn
		AND S.department <> C.department
		AND E.score = (SELECT	Max (score)
					   FROM		enrollments
					   WHERE   	crn = E.crn);