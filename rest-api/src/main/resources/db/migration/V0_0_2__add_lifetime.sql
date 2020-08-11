ALTER TABLE pull_request ADD COLUMN lifetime_minutes bigint;

UPDATE pull_request SET lifetime_minutes = EXTRACT(minute FROM (closed_at - created_at));

ALTER TABLE pull_request ALTER COLUMN lifetime_minutes SET not null;
