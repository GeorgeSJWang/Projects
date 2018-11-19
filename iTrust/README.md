# iTrust (Fall 2018)

Developing New Use Cases:

UC93: Obstetrics Patient Initialization
93.1 Preconditions
An HCP is a registered user of the iTrust Medical Records system (UC2). The iTrust HCP user has authenticated himself or herself in the iTrust Medical Records system (UC3). The patient associated with the obstetrics patient initialization must by a registered patient in the iTrust Medical Records system.

93.2: Main Flow
Any HCP may search for the patient by MID or patient name [S1][E1][E2][E3]. The history of the patient's obstetrics care is displayed as a list of patient initialization records in descending order by date (the most recent record is at the top of the list). Any HCP has the option to select an existing record to view. If the HCP selects to view an existing record, the record is shown as a read only version of the information in [S2][S3]. Only HCPs with a specialization of “OB/GYN” may create a new obstetrics record via a link or button the list of patient initialization records. If the OB/GYN HCP selects to add a new record, the OB/GYN HCP enters information required for the initial obstetrics patient visit including the last menstrual period (LMP) [S2] and prior pregnancies [S3]. The OB/GYN HCP submits the form and is sent to the main page for obstetric records with the listing of the patient's history of obstetrics care.

93.3 Sub-flows
[S1] If the patient is eligible for obstetric care, the obstetric initialization page is shown. The current date of the obstetrics patient initialization is populated in the date field.
[S2] The HCP enters the date of the first day of the patient's last menstrual period (LMP). The estimated due date (EDD) and the number of weeks pregnant on the obstetrics patient initialization date are calculated and displayed.
	EDD = LMP + 280 days
	Day 0 of a week is the day (e.g., Monday, Tuesday, ect.) of the LMP. The LMP is day zero, LMP + 1 is 0 weeks 1 day, LMP + 7 is 1 week 0 days, LMP + 8 is 1 week 1 day, etc. The obstetrics patient initialization shows the number of weeks pregnant on the day of the record creation.
[S3] A prior pregnancy has the following information described in Data Format 6.14 [E4]. The table should be populated with information that already exists in the iTrust system, if any. The HCP adds any additional prior pregnancy information to the table.
	Year of conception
	Number of weeks pregnant (how many weeks and days the pregnancy lasted)
	Number of hours in labor
	Weight gain during pregnancy
	Delivery type: vaginal delivery, vaginal delivery vacuum assist, vaginal delivery forceps assist, caesarean section, miscarriage
	Whether the pregnancy was a multiple, and how many

93.4: Alternative Flows
[E1] The HCP types an invalid medical identification number or name or a medical identification number or name of a patient not in the system and is prompted to try again.
[E2] The patient chosen is not the desired patient. The HCP does not confirm the selection and is prompted to try again.
[E3] The patient is not eligible for obstetric care.  The error message, “The patient is not eligible for obstetric care.” is displayed and the HCP is prompted to try again, or to change the patient’s eligibility for obstetric care.
[E4] Invalid inputs are flagged and an error message appropriate to the input is printed.

UC94: Obstetrics Office Visit
94.1 Preconditions
An HCP is a registered user of the iTrust Medical Records system (UC2). The patient must be a registered patient in the iTrust Medical Records system. The iTrust user has authenticated himself or herself in the iTrust Medical Records system.

94.2 Main Flow
A HCP chooses to document [S2] or edit [S3] an obstetrics office visit for a current obstetrics patient [S1]. A current obstetrics patient is defined as a patient who has been initialized as an obstetrics patient (UC93) with an LMP that is less than 49 weeks, 0 days prior to the office visit date [S4]. An ultrasound may be performed during an office visit [S6]. At the end, the next visit is automatically scheduled for the patient [S7].

94.3 Subflows
[S1] The HCP enters a MID [E1] or name of a patient and confirms their selection [E2][E3].
[S2] An OB/GYN HCP [E4] documents the following information related to an obstetrics office visit and saves the obstetrics office visit record. All events are logged (UC5, S8) and the HCP is sent to an overview of obstetrics office visits for the patient. All information is required [E5] and details are described in Data Format 6.15: Date of the office visit - current date
	Number of weeks pregnant as of the office visit date (generated from last menstrual period).
	Weight in pounds
	Blood pressure
	Fetal heart rate (FHR)
	If the current pregnancy is a multiple, and how many
	Whether a low lying placenta was observed during the visit
[S3] OB/GYN HCPs can return to an obstetrics office visit and modify or delete the fields of the obstetrics office visit. The event is logged (UC5, S8) and the HCP is returned in the specific office visit record to verify his or her changes.
[S4] If multiple obstetrics patient initializations fall within the 49 week window (e.g., two pregnancies within 49 weeks, so two initializations), the most recent obstetrics patient initialization is used.
[S5] If the patient’s RH- flag is set (the blood type is RH negative) and the current pregnancy term is past 28 weeks, a notice is displayed that the patient should be given an RH immune globulin shot if they have not already
[S6] During an ultrasound, an ultrasound record is created. In this record, the HCP can upload a .jpg, .pdf, or .png image of an ultrasound. The following information is recorded for each fetus (there may be multiple):
	Crown rump length (CRL)
	Biparietal diameter (BPD)
	Head circumference (HC)
	Femur length (FL)
	Occipitofrontal diameter (OFD)
	Abdominal circumference (AC)
	Humerus length (HL)
	Estimated fetal weight (EFW)
[S7] The next appointment (office visit or delivery visit) will be scheduled for the patient at the end of the appointment. The patient’s availability will be automatically taken into account via a publicly available Google calendar for the patient [E6]. The user should be able to add the appointment to his/her via an "Add to Google Calendar" button. Appointments are scheduled between 9am and 4pm, Monday - Friday [E7]. Appointment times default to the time of the current office visit. Schedule frequency is computed as follows  The appointment time should be set according to the following schedule. 
	0 - 13 weeks pregnant: Monthly appointments (every 4 weeks)
	14 - 28 weeks pregnant: Appointments every 2 weeks
	29 - 40 weeks pregnant: Appointments weekly
	40 - 42 weeks pregnant: appointments every other weekday [E8]
	Childbirth visits can be scheduled anytime between 37-42 weeks at the request of the patient

94.4 Alternate Flow
[E1] The HCP types an invalid patient medical identification number and is prompted to try again.
[E2] The patient chosen is not the desired patient. The HCP does not confirm the selection and is prompted to try again.
[E3] The patient chosen is not a current obstetrics patient. The HCP is prompted to try again.
[E4] An HCP without the OB/GYN specialization is unable to create or edit an obstetrics office visit. They are prompted to create a regular office visit (UC11).
[E5] Invalid or missing inputs are flagged and an error message appropriate to the input is printed.
[E8] If the patient reaches 42 weeks pregnant, the next visit is a Childbirth Hospital Visit (UC96)

UC95: Labor and Delivery Report
95.1: Preconditions
UC 93, 94, and 96 must be fully implemented (including their pre-requisites) before UC 95 can be implemented.

95.2: Main Flow
Any HCP may search for the patient by MID or patient name [S1][E1][E2]. A report will be generated containing all relevant information about the pregnancy. All HCPs can generate this report in case of emergency.

95.3: Subflows
[S1] - A current labor and delivery report is generated and displayed containing the following information. The generation of the report is logged.
Information for each past pregnancy
	Pregnancy term
	Delivery type
	Conception year
Estimated delivery date
Blood type
Obstetrics Office Visit Information, most recent visit first
	Weeks pregnant at office visit
	Weight at office visit
	Blood pressure at office visit
	Fetal heart rate (FHR) at office visit
	Whether the pregnancy was a multiple, and how many
	f a low lying placenta was observed during the visit
	Complications
Pregnancy complication warning flags
	RH- flag
	igh Blood Pressure (pre-eclampsia)
	High blood pressure is 140/90. If either value, systolic or diastolic, is at or above those ranges, it is considered a complication (i.e., 140/80 is high, 130/90 is high, 139/89 is not high)
	Advanced Maternal Age (This means the patient is 35 or older at the time of delivery (or expected delivery))
	Whether or not the mother has a relevant pre-existing condition (listed later)
	Whether or not there are relevant maternal allergies (listed later)
	Low-lying placenta
	igh genetic potential for miscarriage
	Abnormal fetal heart rate (Normal fetal heart rate range: [120, 160] inclusive)
	Multiples in current pregnancy
	Atypical weight change (Typical weight gain range (in pounds): [15, 35] inclusive)
	Hyperemesis gravidarum
	Hypothyroidism
A list of relevant pre-existing conditions, including:
	Diabetes
	Chronic illness (autoimmune disorders)
	Cancers
	STDs
A list of the mother’s common drug allergies, including:
	Penicillin
	Sulfa drugs
	Tetracycline
	Codeine
	NSAIDs

95.3: Alternate Flows
[E1] The HCP types an invalid medical identification number or name or a medical identification number or name of a patient not in the system and is prompted to try again.
[E2] The selected patient is not eligible for obstetric care or does not have an obstetrics record. The error message, “Selected patient does not have an obstetrics record” is displayed and the HCP is prompted to try again.

UC96: Childbirth Hospital Visit
96.1 Preconditions
An HCP is a registered user of the iTrust Medical Records system (UC2). The patient must be a registered patient in the iTrust Medical Records system. The iTrust user has authenticated himself or herself in the iTrust Medical Records system.

96.2 Main Flow
A patient’s childbirth hospital visit is scheduled apriori during an office visit (UC 94) [S1], or during an Emergency Room visit [S2]. Any HCP may search for the patient by MID or patient name [S1][E1][E2]. The history of the patient's obstetrics care is displayed as a list of patient initialization records in descending order by date (the most recent record is at the top of the list). The HCP specifies the patient’s preferred childbirth method [S3]. Drugs may be administered during labor and delivery [S4]. At the time of delivery [S5], a new patient is created [S6]. All events are logged.

96.3 Subflows
[S1] A patient has a childbirth hospital visit scheduled during a routine office visit (UC 94). Childbirth hospital visits are a new type of visit created for this use case. ApptType=”Childbirth”. The HCP should be able to access or view the initialization record for the patient from the childbirth hospital visit page.
[S2] A patient in labor enters the emergency room and the HCP creates a childbirth hospital visit. There should be a field that allows the HCP to specify if this was a pre-scheduled appointment or if the appointment was made at the time the patient entered the ER (per UC94 [S7]). [E3]
[S3] The preferred childbirth method is specified when the office visit is scheduled per [S1] or [S2]: Delivery type: vaginal delivery, vaginal delivery vacuum assist, vaginal delivery forceps assist, caesarean section, miscarriage. This should use a drop-down or radio-button (or similar) and not be specified solely in the comments.
[S4] Drugs administered during the childbirth are recorded (drug, dosage)
	Pitocin
	Nitrous oxide
	Pethidine
	Epidural anaesthesia
	Magnesium sulfate
	RH immune globulin
[S5] At the time of delivery, the HCP enters the date, time, delivery method (may be different than the preferred method), and sex of each baby that is delivered (in the case of multiples)
[S6] For each baby that is delivered, a new patient file is created (UC1).
[S7] OB/GYN HCPs can return to a childbirth visit record and modify or delete any of the fields. The event is logged.

96.4 Alternate Flows
[E1] The HCP types an invalid medical identification number or name or a medical identification number or name of a patient not in the system and is prompted to try again.
[E2] The patient chosen is not the desired patient. The HCP does not confirm the selection and is prompted to try again.
[E3] The baby is delivered before the patient reaches the hospital or while in the emergency room. Default to vaginal delivery, and proceed with [S5]. A flag should be set to indicate that time of delivery is estimated in this alternate flow.