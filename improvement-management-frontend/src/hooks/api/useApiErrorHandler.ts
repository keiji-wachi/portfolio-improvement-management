import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import { ApiError } from "../../api/client";

export function useApiErrorHandler() {
  const navigate = useNavigate();

  return useCallback(
    (error: unknown) => {
      if (!(error instanceof ApiError)) {
        navigate("/500");
        return;
      }

      switch (error.status) {
        case 401:
          navigate("/login");
          break;
        case 403:
          navigate("/403");
          break;
        case 404:
          navigate("/404");
          break;
        default:
          navigate("/500");
      }
    },
    [navigate]
  );
}