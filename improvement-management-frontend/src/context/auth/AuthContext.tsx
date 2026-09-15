import { createContext, useState } from "react";
import type { ReactNode } from "react";
import type { LoginUser } from "../../types/auth";

type AuthContextType = {
  loginUser: LoginUser | null;
  setLoginUser: React.Dispatch<React.SetStateAction<LoginUser | null>>;
};

export const AuthContext = createContext<AuthContextType | undefined>(
  undefined
);

type Props = {
  children: ReactNode;
};

export function AuthProvider({ children }: Props) {
  const [loginUser, setLoginUser] = useState<LoginUser | null>(null);

  return (
    <AuthContext.Provider
      value={{
        loginUser,
        setLoginUser,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}