import { useState } from "react";

function LoginPage() {
  const [loginId, setLoginId] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();

    console.log("ログインID:", loginId);
    console.log("パスワード:", password);
  };

  return (
    <div>
      <h1>ログイン画面</h1>

      <form onSubmit={handleSubmit}>
        <div>
          <label>ログインID</label>
          <input
            type="text"
            value={loginId}
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

        <button type="submit">ログイン</button>
      </form>
    </div>
  );
}

export default LoginPage;