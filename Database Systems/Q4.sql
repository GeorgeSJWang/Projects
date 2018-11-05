/*

For the table Students, Enrollments, Courses and Departments,
write a SQL query that returns the  NetId  of  Students  from 'CS' Department
whose Score in 'Database Systems' course is higher compared to all non-CS students
taking the course. Sort the NetIds in ascending order.

*/

SELECT S.netid
FROM   students S,
	   enrollments E,
	   courses C
WHERE  S.netid = E.netid
	   AND E.crn = C.crn
	   AND S.department = 'CS'
	   AND C.title = 'Database Systems'
	   AND E.score > (SELECT Max (E.score)
	   				  FROM   students S,
	   				         enrollments E,
	   				         courses C
	   				  WHERE  S.netid = E.netid
	   				  AND	 E.crn   = C.crn
	   				  AND    S.department <> 'CS'
	   				  AND    C.title = 'Database Systems')
ORDER BY S.netid;