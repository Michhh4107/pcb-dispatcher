INSERT INTO printed_circuit_board (id, name, status, created_at)
VALUES
    (nextval('board_id_seq'), 'Board 10', 'REGISTRATION', NOW()),
    (nextval('board_id_seq'), 'Board 20', 'REPAIR', NOW());