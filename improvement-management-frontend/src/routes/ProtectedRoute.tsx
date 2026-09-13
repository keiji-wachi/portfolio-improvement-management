import { Navigate } from "react-router-dom";

import { useAuth } from "../hooks/auth/useAuth";

type Props = {
  children: React.ReactNode;
};

function ProtectedRoute({ children }: Props) {
  const { loginUser } = useAuth();

  if (!loginUser) {
    return <Navigate to="/login" replace />;
  }

  return children;
}

export default ProtectedRoute;