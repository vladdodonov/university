create table course (id uuid not null, name varchar(255), faculty_id uuid, profesor_id uuid, primary key (id));
create table courses_students (course_id uuid not null, student_id uuid not null);
create table faculty (id uuid not null, name varchar(255), primary key (id));
create table professor (id uuid not null, name varchar(255), first_name varchar(255), last_name varchar(255), primary key (id));
create table student (id uuid not null, name varchar(255), first_name varchar(255), last_name varchar(255), primary key (id));
alter table course add constraint facultyFK foreign key (faculty_id) references faculty;
alter table course add constraint professorFK foreign key (profesor_id) references professor;
alter table courses_students add constraint studentFK foreign key (student_id) references student;
alter table courses_students add constraint courseFK foreign key (course_id) references course;
