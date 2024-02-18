CREATE OR REPLACE FUNCTION append_updated_to_name()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.name = NEW.name || ' updated';
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER update_employee_name
    BEFORE UPDATE ON users
    FOR EACH ROW
    WHEN (OLD.name IS DISTINCT FROM NEW.name)
EXECUTE FUNCTION append_updated_to_name();