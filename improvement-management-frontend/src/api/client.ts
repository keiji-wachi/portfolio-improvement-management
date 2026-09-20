const API_BASE_URL =
  import.meta.env.VITE_API_BASE_URL;

export class ApiError extends Error {
  status: number;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
  }
}

export async function apiFetch(
  path: string,
  options: RequestInit = {}
) {
const response = await fetch(`${API_BASE_URL}${path}`, {
  ...options,
  credentials: "include",
  headers: {
    "Content-Type": "application/json",
    ...options.headers,
  },
});

  if (!response.ok) {
    const errorBody = await response.json();

    throw new ApiError(
      response.status,
      errorBody.message ?? `API Error: ${response.status}`
    );
  }

  return response;
}