import { useState } from "react";
import { useAuth } from "../../hooks/auth/useAuth";
import { login } from "../../api/auth/authApi";
import { useNavigate } from "react-router-dom";
import "../../styles/auth/LoginForm.css";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { ApiError } from "../../api/client";
import ErrorMessage from "../common/ErrorMessage";

function LoginForm() {
  const [loginId, setLoginId] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  const [errors, setErrors] = useState<{
    loginId?: string;
    password?: string;
  }>({});

  const { setLoginUser } = useAuth();
  const navigate = useNavigate();
  const handleApiError = useApiErrorHandler();

  const validate = () => {
    const newErrors: {
      loginId?: string;
      password?: string;
    } = {};

    if (!loginId.trim()) {
      newErrors.loginId = "社員番号を入力してください";
    }

    if (!password) {
      newErrors.password = "パスワードを入力してください";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  const loginAuth = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    setIsLoading(true);
    setErrorMessage("");

    try {
      const data = await login({
        employeeNo: loginId,
        password,
      });

      setLoginUser(data);
      navigate("/users");

    } catch (error) {
      if (error instanceof ApiError && error.status === 401) {
        setErrorMessage(error.message);
        return;
      }

      handleApiError(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="login-card">
      <div className="login-header">
        <h2>ログイン</h2>
        <p>アカウントにサインインしてください</p>
      </div>

      <div className="error-message">
        <ErrorMessage message={errorMessage} />
      </div>

      <form className="login-form" onSubmit={loginAuth}>
        <div className="login-field">
          <label htmlFor="employeeNo">
            社員番号
          </label>

          <input
            id="employeeNo"
            type="text"
            value={loginId}
            onChange={(e) => setLoginId(e.target.value)}
            placeholder="社員番号を入力してください"
          />

          {errors.loginId && (
            <p className="field-error">
              {errors.loginId}
            </p>
          )}
        </div>

        <div className="login-field">
          <label htmlFor="password">
            パスワード
          </label>

          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="パスワードを入力してください"
          />

          {errors.password && (
            <p className="field-error">
              {errors.password}
            </p>
          )}
        </div>

      <button
        className="login-button"
        type="submit"
        disabled={isLoading}
        >{isLoading ? ("ログイン中...") : (<>ログイン<span className="login-button-arrow">→</span></>)}
      </button>
      </form>
    </div>
  );
}

export default LoginForm;