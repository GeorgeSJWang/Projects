/*Inserting patient Brenna Lowery*/
INSERT INTO patients
(MID, 
lastName, 
firstName,
email,
address1,
address2,
city,
state,
zip,
phone,
dateofbirth,
Gender)
VALUES
(1,
'Lowery', 
'Brenna', 
'lowery@gmail.com', 
'1333 Who Cares Road', 
'Suite 103', 
'Raleigh', 
'NC', 
'27606-1234', 
'919-971-0001',
'1977-03-15',
'Female')
 ON DUPLICATE KEY UPDATE MID = MID;
