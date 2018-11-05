/*

For the table Departments, write a SQL query that returns the name 
(the column Department) of departments that are bigger
(in terms of  NumberOfStudents) compared to at least one other department.

*/


SELECT department
FROM   departments
WHERE  numberofstudents > ANY (SELECT numberofstudents
					 		   FROM   departments);