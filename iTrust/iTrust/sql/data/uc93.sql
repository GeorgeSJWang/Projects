/*Inserting prior pregnancies*/
INSERT INTO pregnancyrecords(
PatientID,
YOC,
num_weeks_pregnant,
num_hours_labor,
weight_gain,
delivery_type,
num_children,
Date_delivery
)
VALUES (
1,
2011,
41,
14,
10,
'caesarean section',
2,
'2011-10-1'
)ON DUPLICATE KEY UPDATE ID = ID;

/*Inserting obstetrics records*/
INSERT INTO obstetricsrecords(
ID,
PatientID,
LMP,
number_of_weeks_pregnant,
created_on)
VALUES (
1,
1,
'2018-10-01',
4,
'2018-10-30'
)ON DUPLICATE KEY UPDATE ID = ID;
