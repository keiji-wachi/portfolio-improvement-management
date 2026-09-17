import LoginForm from "../../components/auth/LoginForm";
import "../../styles/auth/LoginPage.css";

function LoginPage() {
  return (
    <main className="login-page">
      <section className="login-brand-panel">
        <div className="login-brand-content">
          <h1>改善管理システム</h1>

          <h2>
            工場・製造業の
            <br />
            スマート管理
          </h2>

          <p>
            異常対応から改善管理、統計分析までを一元管理。
            現場の情報を可視化し、改善活動を支援します。
          </p>
        </div>
      </section>

      <section className="login-form-panel">
        <LoginForm />
      </section>
    </main>
  );
}

export default LoginPage;