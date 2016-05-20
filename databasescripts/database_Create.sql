create database mentalhealthcare;
use mentalhealthcare;

create TABLE Kontakt (
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50),
adresse varchar(100),
telefonNr varchar(12),
typ varchar(25),
patientId int,
FOREIGN KEY (patientId ) REFERENCES patient(id)
);

create table ICDCDiagnose(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(100) not null
);

create Table Arzt(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50),
adresse varchar(100),
spezialgebiet varchar(50)
);

create TABLE Diagnose(
id int not null PRIMARY KEY AUTO_INCREMENT,
active SMALLINT,
arztId int,
patientId int,
icdcId int,
FOREIGN KEY (icdcId) REFERENCES ICDCDiagnose(id),
FOREIGN KEY (patientId ) REFERENCES patient(id),
FOREIGN KEY (arztId) REFERENCES arzt(id)   
);

create table compendiumMedikament(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50)
);

create table Medikament(
id int not null PRIMARY KEY AUTO_INCREMENT,
dosis varchar(50),
arztId int,
compMedId int,
active SMALLINT,
patientId int,
FOREIGN KEY (patientId) REFERENCES patient(id),
FOREIGN KEY (arztId) REFERENCES arzt(id),
FOREIGN KEY (compMedId) REFERENCES compendiummedikament(id)    
);

create TABLE patient(
id int not null PRIMARY KEY AUTO_INCREMENT,
name varchar(50),
vorname varchar(50),
gebDatum date,
svNr varchar(25),
status int
)


