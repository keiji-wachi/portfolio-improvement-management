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
    'システム管理者ユーザー',
    1,
    1,
    FALSE,
    '$2a$10$0qVXgz1nOlroKxVBgXzU1.EBWM9Fp01sYvisKlidsH82r9iWlRo9C',
    'admin0001',
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
    '指導員ユーザー',
    1,
    2,
    FALSE,
    '$2a$10$0qVXgz1nOlroKxVBgXzU1.EBWM9Fp01sYvisKlidsH82r9iWlRo9C',
    'instructor0001',
    FALSE
);