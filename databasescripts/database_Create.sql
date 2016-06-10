drop database mentalhealthcare;
create database mentalhealthcare;
use mentalhealthcare;

create TABLE patient(
id int not null PRIMARY KEY AUTO_INCREMENT,
surename varchar(50),
firstname varchar(50),
birthdate date,
assuranceNr varchar(25),
state int
);

create TABLE Contact (
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50),
adress varchar(100),
phoneNr varchar(12),
contactType varchar(25),
patientId int,
FOREIGN KEY (patientId) REFERENCES patient(id)
);

create table ICDCDiagnose(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(100) not null
);

create Table Doctor(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50),
adress varchar(100),
specialisation varchar(50),
username varchar(50),
password varchar(50)

);

create TABLE Diagnose(
id int not null PRIMARY KEY AUTO_INCREMENT,
active SMALLINT,
doctorId int,
patientId int,
icdcId int,
FOREIGN KEY (icdcId) REFERENCES ICDCDiagnose(id),
FOREIGN KEY (patientId) REFERENCES patient(id),
FOREIGN KEY (doctorId) REFERENCES doctor(id)   
);

create table compendiumMedicament(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50),
maxDose decimal,
maxDosePerDay int
);

create table Medicament(
id int not null PRIMARY KEY AUTO_INCREMENT,
dose decimal,
doctorId int,
compMedId int,
active SMALLINT,
patientId int,
takeings int,
FOREIGN KEY (patientId) REFERENCES patient(id),
FOREIGN KEY (doctorId) REFERENCES doctor(id),
FOREIGN KEY (compMedId) REFERENCES compendiummedicament(id)    
);

