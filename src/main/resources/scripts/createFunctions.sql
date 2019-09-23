CREATE OR REPLACE FUNCTION not_remove() RETURNS trigger AS
$body$
BEGIN
if old."name" = 'Eminem' then
Raise exception 'not remove record with name = Eminem';
end if;
  RETURN old;
END;
$body$
LANGUAGE 'plpgsql';


create trigger on_remove
before delete on artists for each row
execute procedure not_remove();


CREATE FUNCTION get_max_copies_count_name() RETURNS varchar AS $$
    SELECT name FROM albums WHERE copies_count = (select max(copies_count) from albums);
   $$ LANGUAGE SQL;