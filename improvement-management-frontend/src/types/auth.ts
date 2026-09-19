export type LoginUser = {
  userId: number;
  name: string;

  departmentId: number;
  departmentName: string;

  roleId: number;

  firstLoginFlag: boolean;
  success: boolean;
};