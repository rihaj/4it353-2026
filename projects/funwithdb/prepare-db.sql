create schema APP;

create user APP_USER password 'APP_USER';

grant all on schema APP to APP_USER;

use APP;

create table STUDENTS (
  ID bigint auto_increment not null,
  ACTIVE boolean not null default true,
  LOGIN varchar not null,
  NAME varchar not null,
  DATE_OF_BIRTH date not null,
  BALANCE decimal(10, 2) not null default 0,
  PHOTO blob,
  LAST_LOGIN timestamp
);

alter table STUDENTS add constraint STUDENTS_PK
  primary key (ID);

create table COURSES (
  ID bigint auto_increment not null,
  NAME varchar not null,
  CREDITS integer not null
);

alter table COURSES add constraint COURSES_PK
  primary key (ID);

create table ENROLLMENTS (
  ID bigint auto_increment not null,
  STUDENT_ID bigint not null,
  COURSE_ID bigint not null,
  SEMESTER integer not null,
  GRADE varchar(1)
);

alter table ENROLLMENTS add constraint ENROLLMENTS_PK
  primary key (ID);

alter table ENROLLMENTS add constraint ENROLLMENTS_UQ1
  unique (STUDENT_ID, COURSE_ID, SEMESTER);

alter table ENROLLMENTS add constraint ENROLLMENT_FK_STUDENT
  foreign key (STUDENT_ID) references STUDENTS(ID);

alter table ENROLLMENTS add constraint ENROLLMENT_FK_COURSE
  foreign key (COURSE_ID) references COURSES(ID);

insert into STUDENTS (ID, LOGIN, NAME, DATE_OF_BIRTH)
  values (1, 'pufm', 'Muf Puf', current_timestamp - 5000);

insert into STUDENTS (ID, ACTIVE, LOGIN, NAME, DATE_OF_BIRTH, BALANCE)
  values (2, false, 'barf', 'Foo Bar', '1984-02-29', 147.39);

insert into COURSES (ID, NAME, CREDITS)
  values (1,'4IT101 Programování v Javě',7);

insert into COURSES (ID, NAME, CREDITS)
  values (2,'4IT115 Softwarové inženýrství',6);

insert into COURSES (ID, NAME, CREDITS)
  values (3,'4IT353 Klient-server aplikace v Javě',6);

insert into ENROLLMENTS (ID, STUDENT_ID, COURSE_ID, SEMESTER, GRADE)
  values (1, 1, 1, 1, 2);

insert into ENROLLMENTS (ID, STUDENT_ID, COURSE_ID, SEMESTER, GRADE)
  values (2, 1, 2, 2, 1);

insert into ENROLLMENTS (ID, STUDENT_ID, COURSE_ID, SEMESTER)
  values (3, 1, 3, 2);

insert into ENROLLMENTS (ID, STUDENT_ID, COURSE_ID, SEMESTER, GRADE)
  values (4, 2, 1, 1, 4);

insert into ENROLLMENTS (ID, STUDENT_ID, COURSE_ID, SEMESTER, GRADE)
  values (5, 2, 1, 2, 4);

commit;
