INSERT INTO user_mst (
    id,
    name,
    department_id,
    role_id,
    first_login_flag,
    password,
    employee_number,
    is_deleted
)
VALUES (
    2,
    'テストユーザー',
    3,
    1,
    FALSE,
    '$2a$10$0qVXgz1nOlroKxVBgXzU1.EBWM9Fp01sYvisKlidsH82r9iWlRo9C',
    'test0001',
    FALSE
);

INSERT INTO user_mst (
    id,
    name,
    department_id,
    role_id,
    first_login_flag,
    password,
    employee_number,
    is_deleted
)
VALUES (
    3,
    'テストリリーフユーザー',
    1,
    3,
    FALSE,
    '$2a$10$0qVXgz1nOlroKxVBgXzU1.EBWM9Fp01sYvisKlidsH82r9iWlRo9C',
    'test0002',
    FALSE
);

INSERT INTO user_mst (
    id,
    name,
    department_id,
    role_id,
    first_login_flag,
    password,
    employee_number,
    is_deleted
)
VALUES (
    4,
    'テスト作業者ユーザー',
    1,
    4,
    FALSE,
    '$2a$10$0qVXgz1nOlroKxVBgXzU1.EBWM9Fp01sYvisKlidsH82r9iWlRo9C',
    'test0003',
    FALSE
);