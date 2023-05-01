ALTER TABLE link
    ADD COLUMN
        last_check_time TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now();

ALTER TABLE link
    ADD COLUMN
        last_update_time TIMESTAMP WITH TIME ZONE DEFAULT now();