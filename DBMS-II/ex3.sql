Create or replace function teach_trigger_func() returns trigger as $$
Declare 
count integer :=0;
time_slot varchar(1);
course record;
BEGIN
    time_slot := (select time_slot_id from section where section.course_id=new.course_id and section.year=new.year and section.semester=new.semester and section.sec_id=new.sec_id); 
    for course in(SELECT course_id from teaches t where t.id=new.id and t.semester=new.semester and t.year=new.year)
    loop
        if((select count(*) from section where section.course_id=course.course_id and section.time_slot_id=time_slot)>0)  then count=2; end if;
    end loop;
    if (count>0)
    THEN
      RAISE EXCEPTION 'Classes Clash from trigger on teaches';
    end if;
    return new;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE TRIGGER teach_trigger
  before insert or update on teaches
  FOR EACH ROW
execute procedure teach_trigger_func();


Create or replace function section_trigger_func() returns trigger as $$
Declare 
count integer :=0;
tid varchar(5);
course record;
BEGIN
    tid := (select id from teaches where course_id=new.course_id and year=new.year and semester=new.semester); 
    for course in(SELECT course_id from teaches t where t.id=tid and t.semester=new.semester and t.year=new.year)
    loop
        if((select count(*) from section where section.course_id=course.course_id and section.time_slot_id=new.time_slot_id)>0)  then count=2; end if;
    end loop;
    if (count>0)
    THEN
      RAISE EXCEPTION 'Classes Clash on section trigger';
    end if;
    return new;
END;
$$ LANGUAGE plpgsql;
CREATE OR REPLACE TRIGGER section_trigger
  BEFORE INSERT OR UPDATE on section
  FOR EACH ROW
execute procedure section_trigger_func();