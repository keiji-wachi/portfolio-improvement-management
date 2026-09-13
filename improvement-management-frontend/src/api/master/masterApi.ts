import { apiFetch } from "../client";
import type { Department, Role } from "../../types/master";

export async function getDepartments(): Promise<Department[]> {
  const response = await apiFetch("/msts/departments");
  return response.json();
}

export async function getRoles(): Promise<Role[]> {
  const response = await apiFetch("/msts/roles");
  return response.json();
}