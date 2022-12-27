CREATE TABLE IF NOT EXISTS courses
(
    course_id SERIAL,
    course_name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    course_description varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT courses_pkey PRIMARY KEY (course_id)
);
    
CREATE TABLE IF NOT EXISTS groups
(
    group_id SERIAL,
    group_name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT groups_pkey PRIMARY KEY (group_id)
);

CREATE TABLE IF NOT EXISTS students
(
    student_id SERIAL,
    group_id integer,
    first_name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    last_name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

CREATE TABLE IF NOT EXISTS teachers
(
    teacher_id SERIAL,
    first_name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    last_name varchar(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT teachers_pkey PRIMARY KEY (teacher_id)
);

CREATE TABLE IF NOT EXISTS timetables
(
    timetable_id SERIAL,
    CONSTRAINT timetables_pkey PRIMARY KEY (timetable_id)
);

CREATE TABLE IF NOT EXISTS events
(
    event_id SERIAL,
    event_date date,
    event_time time,
    course_id integer,
    group_id integer,
    teacher_id integer,
    CONSTRAINT eventss_pkey PRIMARY KEY (event_id)
);

CREATE TABLE students_courses
( 
 	student_id integer REFERENCES students(student_id) ON DELETE CASCADE, 
 	course_id integer REFERENCES courses(course_id) ON DELETE CASCADE, 
 	PRIMARY KEY(student_id, course_id) 
);

CREATE TABLE teachers_groups
( 
 	teacher_id integer REFERENCES teachers(teacher_id) ON DELETE CASCADE, 
 	group_id integer REFERENCES groups(group_id) ON DELETE CASCADE, 
 	PRIMARY KEY(teacher_id, group_id) 
);

CREATE TABLE timetables_events
( 
 	timetable_id integer REFERENCES timetables(timetable_id) ON DELETE CASCADE, 
 	event_id integer REFERENCES events(event_id) ON DELETE CASCADE, 
 	PRIMARY KEY(timetable_id, event_id) 
);