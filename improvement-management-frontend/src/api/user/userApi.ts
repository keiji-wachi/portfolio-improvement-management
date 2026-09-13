import { apiFetch } from "../client";
import type { User } from "../../types/user";

export async function getUsers(): Promise<User[]> {
  const response = await apiFetch("/users");
  return response.json();
}

export async function getUserById(id: number): Promise<User> {
  const response = await apiFetch(`/users/${id}`);
  return response.json();
}

export async function createUser(body: {
  employeeNo: string;
  name: string;
  departmentId: number;
  roleId: number;
  password: string;
}) {
  return apiFetch("/users", {
    method: "POST",
    body: JSON.stringify(body),
  });
}

export async function updateUser(
  id: number,
  body: {
    name: string;
    departmentId: number;
    roleId: number;
  }
) {
  return apiFetch(`/users/${id}`, {
    method: "PUT",
    body: JSON.stringify(body),
  });
}

export async function deleteUser(id: number) {
  return apiFetch(`/users/${id}`, {
    method: "DELETE",
  });
}