ALTER TABLE assets
ADD user_id uuid not null
DEFAULT ('00000000-0000-0000-0000-000000000000');

UPDATE assets
SET user_id = (SELECT id FROM users limit 1)
WHERE assets.user_id = '00000000-0000-0000-0000-000000000000';

ALTER TABLE assets
ADD CONSTRAINT fk_user_id_asset
FOREIGN KEY (user_id) REFERENCES users(id);
