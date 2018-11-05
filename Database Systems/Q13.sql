/*

For the table Students, Enrollments, Courses and Departments, write a SQL query that
returns Title of each course and the number of students whose score in the course is
higher than the average score for all students taking the course.

*/

SELECT DISTINCT  C.title, Count(E.netid)
FROM	enrollments E, courses C
WHERE   E.crn = C.crn AND E.score > (SELECT Avg (score)
									 FROM   enrollments
									 WHERE  crn = E.crn)
GROUP BY E.crn, C.title;