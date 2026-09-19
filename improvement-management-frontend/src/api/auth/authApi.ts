import { apiFetch } from "../client";
import type { LoginUser } from "../../types/auth";

type LoginRequest = {
  employeeNo: string;
  password: string;
};

export async function login(
  body: LoginRequest
): Promise<LoginUser> {

  const response = await apiFetch("/login", {
    method: "POST",
    body: JSON.stringify(body),
  });

  return response.json();
}

export async function logout(): Promise<void> {
  await apiFetch("/logout", {
    method: "POST",
  });
}