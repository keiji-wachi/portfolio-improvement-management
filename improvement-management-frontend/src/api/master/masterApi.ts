import { apiFetch } from "../client";
import type { Department, IncidentType, Process, Role } from "../../types/master";

export async function getDepartments(): Promise<Department[]> {
  const response = await apiFetch("/msts/departments");
  return response.json();
}

export async function getRoles(): Promise<Role[]> {
  const response = await apiFetch("/msts/roles");
  return response.json();
}

export async function getProcesses(): Promise<Process[]> {
  const response = await apiFetch("/msts/processes");
  return response.json();
}

export async function getIncidentTypes(): Promise<IncidentType[]> {
  const response = await apiFetch("/msts/incident-types");
  return response.json();
}