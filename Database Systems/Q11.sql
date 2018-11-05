/*

For the table Students, Enrollments, Courses, Departments and Faculty, write a SQL query
that returns the LastName of inactive students and faculty members.
A student is inactive if he/she is not enrolled in any courses.
A faculty member is inactive if he/she is not instructing any courses and
is not serving as a department head.

*/

SELECT	S.lastname
FROM	students S
WHERE   S.netid NOT IN (SELECT	E2.netid
						FROM	enrollments E2)
UNION
SELECT	lastname
FROM	faculty
WHERE   Concat(firstname, ' ', lastname) NOT IN (SELECT	departmenthead
												 FROM   departments)
		AND Concat(firstname, ' ', lastname) NOT IN (SELECT instructor
													 FROM   courses);