/*Inserting Kathryn Evans*/
INSERT INTO personnel(
MID,
AMID,
role,
lastName, 
firstName, 
address1,
address2,
city,
state,
zip,
phone,
specialty,
email)
VALUES (
9000000012,
null,
'hcp',
'Evans',
'Kathryn',
'10078 Avent Ferry Road',
'',
'Capitol City',
'NC',
'28700-0458',
'555-877-5100',
'OB/GYN',
'evans@iTrust.org'
)ON DUPLICATE KEY UPDATE MID = MID;

INSERT INTO users(MID, password, role, sQuestion, sAnswer) VALUES(9000000012, '30c952fab122c3f9759f02a6d95c3758b246b4fee239957b2d4fee46e26170c4', 'hcp', 'first letter?', 'a')
ON DUPLICATE KEY UPDATE MID = MID;
/*password: pw*/

INSERT INTO hcpassignedhos(HCPID, HosID) VALUES(9000000012,'4'), (9000000012,'4')
ON DUPLICATE KEY UPDATE HCPID = HCPID;