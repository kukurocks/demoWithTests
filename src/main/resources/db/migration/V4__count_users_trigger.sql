CREATE OR REPLACE FUNCTION total_employee_count()
    RETURNS TRIGGER AS
$$
DECLARE
    total INTEGER;
BEGIN
    SELECT COUNT(*) INTO total FROM users;
    RAISE LOG 'Total employees after insertion: %', total;
    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER log_total_employees
    AFTER INSERT ON users
EXECUTE FUNCTION total_employee_count();