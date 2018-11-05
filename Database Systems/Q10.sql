/*

For the table Students, Enrollments and Courses, write a SQL query that returns
the pair of FirstName for Students who are enrolled in the exactly same courses.
The resultant relation will list a pair once, having the alphabetically smaller
Firstname in first column and the alphabetically bigger Firstname in second column.
Also, the resultant relation will not have self pairs
(a self pair involves two FirstNames of a single student).

*/

SELECT S1.firstname, S2.firstname
FROM   (SELECT E1.netid AS n1, E2.netid AS n2, Count (*) AS nosc
		FROM   enrollments E1, enrollments E2
		WHERE  E1.netid <> E2.netid AND E1.crn = E2.crn
		GROUP BY E1.netid, E2.netid) AS tmp,
		students AS S1,
		students AS S2

WHERE	nosc = (SELECT	Count (*)
				FROM	enrollments
				WHERE   netid = n1)
		AND nosc = (SELECT	Count (*)
					FROM  	enrollments
					WHERE   netid = n2)
		AND S1.netid =  n1
		AND S2.netid =  n2
		AND S1.firstname < S2.firstname