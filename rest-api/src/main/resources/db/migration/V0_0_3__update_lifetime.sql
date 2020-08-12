UPDATE pull_request SET lifetime_minutes = EXTRACT(EPOCH FROM (closed_at - created_at))/60;
