/*

For the table Students, Enrollments, Courses and Departments, write a SQL query that
returns the NetId of all students who are taking courses offered by their DepartmentHead. 

*/

SELECT	S.netid
FROM	students S, enrollments E, courses C, departments D
WHERE   S.netid = E.netid AND E.crn = C.crn
		AND S.department = C.department
		AND C.instructor = D.departmenthead;