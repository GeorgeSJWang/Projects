/*

For the table Students, Enrollments and Courses, write a SQL query that returns 
the FirstName and number of CS courses (Department = 'CS') for all students
who are enrolled in one or more CS courses.
Sort your results first by number of CS courses (in descending order)
and then by FirstName  (in ascending order).

*/

SELECT		S.firstname,
			Count(E.crn) AS numCSCrs
FROM   		students S,
       		enrollments E,
       		courses C
WHERE  		S.netid = E.netid
       		AND   E.crn = C.crn
       		AND   C.department = 'CS'
GROUP BY   	S.netid,
          	S.firstname
HAVING 		Count(E.crn) > 0
ORDER BY 	numcscrs DESC,
         	S.firstname ASC