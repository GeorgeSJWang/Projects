INSERT INTO patients
(MID, 
firstName,
lastName, 
email,
address1,
address2,
city,
state,
zip,
phone,
eName,
ePhone,
iCName,
iCAddress1,
iCAddress2,
iCCity, 
ICState,
iCZip,
iCPhone,
iCID,
DateOfBirth,
MotherMID,
FatherMID,
BloodType,
Ethnicity,
Gender,
TopicalNotes
)
VALUES (
22,
'Fozzie',
'Bear',
'wakka.wakka@wakka.com',
'344 Henson Street',
'',
'New York',
'NY',
'10001',
'555-555-5555',
'Kermit the Frog',
'555-555-5551',
'IC',
'Street1',
'Street2',
'City',
'PA',
'19003-2715',
'555-555-5555',
'1',
'1976-04-25',
0,
0,
'O-',
'Ursine',
'Male',
"Fozzie has a low tolerance for pain--he simply can\'t bear it.  Wakka, Wakka, Wakka."
)  ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) 
			VALUES (22, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'patient', 'how you doin?', 'good')
 ON DUPLICATE KEY UPDATE MID = MID;
/*password: pw*/

INSERT INTO declaredhcp(PatientID,HCPID) VALUE(22, 9000000007)
 ON DUPLICATE KEY UPDATE PatientID = PatientID;

