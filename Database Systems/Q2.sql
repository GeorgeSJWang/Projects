/*

For the table Departments and Faculty, write a SQL query that returns
the name (the column Department) of Departments, in which the ratio of
the number of faculty members and the number of students (NumberOfStudents)
is greater than 0.25.

*/


SELECT D.department
FROM   departments D
WHERE  D.numberofstudents < 4 *	(SELECT Count (*)
								 FROM   faculty F
								 WHERE  F.department = D.department);