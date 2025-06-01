CREATE TABLE account_events (
    id SERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    event_timestamp TIMESTAMP,
    event_data TEXT
);

CREATE TABLE client_events (
    id SERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    event_timestamp TIMESTAMP,
    event_data TEXT
);
