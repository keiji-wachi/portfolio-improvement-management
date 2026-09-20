import {
  createContext,
  useEffect,
  useState,
} from "react";

import type {
  ReactNode,
} from "react";

import type {
  LoginUser,
} from "../../types/auth";

import {
  getCurrentUser,
} from "../../api/auth/authApi";

type AuthContextType = {

  loginUser: LoginUser | null;

  setLoginUser:
    React.Dispatch<
      React.SetStateAction<
        LoginUser | null
      >
    >;

  clearLoginUser: () => void;

  isLoading: boolean;
};

export const AuthContext =
  createContext<
    AuthContextType | undefined
  >(undefined);

type Props = {
  children: ReactNode;
};

export function AuthProvider({
  children,
}: Props) {

  const [
    loginUser,
    setLoginUser,
  ] = useState<LoginUser | null>(
    null
  );

  const [
    isLoading,
    setIsLoading,
  ] = useState(true);

  useEffect(() => {

    const restoreLoginUser =
      async () => {

        try {

          const user =
            await getCurrentUser();

          setLoginUser(user);

        } catch {

          setLoginUser(null);

        } finally {

          setIsLoading(false);
        }
      };

    restoreLoginUser();

  }, []);

  const clearLoginUser = () => {
    setLoginUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        loginUser,
        setLoginUser,
        clearLoginUser,
        isLoading,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}