/*

For the table Students, Enrollments, Courses and Departments, write a SQL query that
returns the DepartmentHead for Departments w here at least 50% of NumberOfStudents
are enrolled in at least one course.

*/


SELECT departmenthead
FROM   departments D
WHERE  numberofstudents * 0.5 <= (SELECT Count(DISTINCT S.netid)
								  FROM   students S,
								         enrollments E
								  WHERE  S.netid = E.netid
								         AND   S.department = D.department)