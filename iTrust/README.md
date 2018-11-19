# iTrust (Fall 2018)

Developing New Use Cases:

UC93: Obstetrics Patient Initialization

Main Flow

Any HCP may search for the patient by MID or patient name. The history of the patient's obstetrics care is displayed as a list of patient initialization records in descending order by date. Any HCP has the option to select an existing record to view. If the HCP selects to view an existing record, the record is shown as a read only version of the information. Only HCPs with a specialization of “OB/GYN” may create a new obstetrics record via a link or button the list of patient initialization records. If the OB/GYN HCP selects to add a new record, the OB/GYN HCP enters information required for the initial obstetrics patient visit including the last menstrual period (LMP) and prior pregnancies. The OB/GYN HCP submits the form and is sent to the main page for obstetric records with the listing of the patient's history of obstetrics care.

UC94: Obstetrics Office Visit

Main Flow

A HCP chooses to document or edit an obstetrics office visit for a current obstetrics patient. A current obstetrics patient is defined as a patient who has been initialized as an obstetrics patient (UC93) with an LMP that is less than 49 weeks, 0 days prior to the office visit date. An ultrasound may be performed during an office visit. At the end, the next visit is automatically scheduled for the patient.


UC95: Labor and Delivery Report

Main Flow

Any HCP may search for the patient by MID or patient name. A report will be generated containing all relevant information about the pregnancy. All HCPs can generate this report in case of emergency.


UC96: Childbirth Hospital Visit

Main Flow

A patient’s childbirth hospital visit is scheduled apriori during an office visit (UC 94), or during an Emergency Room visit. Any HCP may search for the patient by MID or patient name. The history of the patient's obstetrics care is displayed as a list of patient initialization records in descending order by date. The HCP specifies the patient’s preferred childbirth method. Drugs may be administered during labor and delivery. At the time of delivery, a new patient is created. All events are logged.
