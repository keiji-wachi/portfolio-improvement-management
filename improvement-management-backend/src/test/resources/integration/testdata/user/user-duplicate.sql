INSERT INTO role_mst (role_id, role_code, role_name)
VALUES
(1, 'SYSTEM_ADMIN', 'システム管理者'),
(2, 'INSTRUCTOR', '指導員'),
(3, 'RELIEF', 'リリーフ'),
(4, 'WORKER', '作業者');

INSERT INTO department_mst (department_id, department_name)
VALUES
(1, 'プレス'),
(2, '組立'),
(3, '塗装');

INSERT INTO user_mst (
    name,
    department_id,
    role_id,
    password,
    first_login_flag,
    employee_number,
    is_deleted
)
VALUES (
    '既存テストユーザー',
    1,
    4,
    '$2a$10$dummyHash',
    FALSE,
    'test0001',
    FALSE
);