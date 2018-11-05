/*

For the table Students, Enrollments, Courses and Departments:
1. Write a SQL statement that deletes the records of students from Students table
   whose average score for 'CS' courses is less than 80%.
2. Write a SQL query that returns the current Students table.

*/

DELETE FROM students
WHERE  netid IN (SELECT	x.netid
				 FROM 	(SELECT 	s1.netid
				 		 FROM   	students s1, enrollments e, courses c, departments d
				 		 WHERE  	s1.netid = e.netid
				 		 			AND e.crn = c.crn
				 		 			AND c.department = 'CS' 
				 		 GROUP BY 	s1.netid
				 		 HAVING 	Avg (e.score) < 80) AS x
				);

SELECT *
FROM   students;