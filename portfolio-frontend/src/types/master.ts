export type Department = {
  departmentId: number;
  departmentName: string;
}

export type Role = {
  roleId: number;
  roleName: string;
}

export type LoginUser = {
  id: number;
  department_id: number;
  role_id: number;
  succes: boolean;
}