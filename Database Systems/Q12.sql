/*

For the table Students, Enrollments, Courses and Departments, write a SQL query
that returns the NetId of Students whose Score in a non-departmental course is higher
compared to at least one departmental student taking the course.
Sort the NetIds in ascending order.
Also, the resultant relation should not list a  NetId  more than once.

*/

SELECT DISTINCT S.netid
FROM	students S, enrollments E, courses C
WHERE   S.netid = E.netid AND E.crn = C.crn
		AND S.department != C.department
		AND E.score > ANY (SELECT E1.score
						   FROM   students S1, enrollments E1, courses C1
						   WHERE  S1.netid = E1.netid
						          AND E1.crn = E.crn
						          AND S1.department = C.department)
GROUP BY S.netid