/*

For the table Students, Enrollments and Courses, write a SQL query that returns
the NetId and Department of all students who are taking courses offered by
a different department than their own department.

*/

SELECT	netid, department
FROM 	students
WHERE   EXISTS (SELECT courses.department
				FROM   enrollments, courses
				WHERE  enrollments.crn = courses.crn
				AND    enrollments.netid = students.netid
				AND    courses.department <> students.department);