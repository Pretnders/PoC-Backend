CREATE TRIGGER after_like_insert
AFTER INSERT ON likes
FOR EACH ROW
EXECUTE FUNCTION check_and_create_match();