import { useState } from "react";
import { useAuth } from "../../hooks/auth/useAuth";
import { login } from "../../api/auth/authApi";
import { useNavigate } from "react-router-dom";

function LoginForm() {
  const [loginid, setLoginId] = useState("");
  const [password, setPassword] = useState("");

  const { setLoginUser } = useAuth();
  const navigate = useNavigate();

  const loginAuth = async () => {
  const data = await login({
    employeeNo: loginid,
    password,
  });

  setLoginUser(data);
  navigate("/incidents");
};

  return (
    <div>
      <h2>ログイン画面</h2>

      <div>
        <label>ログインID</label>
        <input
          value={loginid}
          onChange={(e) => setLoginId(e.target.value)}
        />
      </div>

      <div>
        <label>パスワード</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      <button onClick={loginAuth}>
        ログイン
      </button>
    </div>
  );
}

export default LoginForm;