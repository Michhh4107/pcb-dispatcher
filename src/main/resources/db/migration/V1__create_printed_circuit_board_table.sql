CREATE TABLE IF NOT EXISTS printed_circuit_board(
    id serial PRIMARY KEY,
    name varchar(64) NOT NULL,
    status varchar(64) NOT NULL,
    created_at TIMESTAMP NOT NULL
)