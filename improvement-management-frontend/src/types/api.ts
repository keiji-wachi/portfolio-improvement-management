export type ApiErrorResponse = {
  status: number;
  message: string;
  errors?: Record<string, string>;
};