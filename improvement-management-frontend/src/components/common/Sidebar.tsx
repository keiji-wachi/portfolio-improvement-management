import { NavLink } from "react-router-dom";
import "../../styles/common/Sidebar.css";

function Sidebar() {
  return (
    <aside className="sidebar">

      <div className="sidebar-header">
        <h2>改善管理システム</h2>
        <p>管理パネル</p>
      </div>

      <nav className="sidebar-nav">

        <NavLink
          to="/users"
          className={({ isActive }) =>
            isActive
              ? "sidebar-link active"
              : "sidebar-link"
          }
        >
          <span className="sidebar-icon">👥</span>
          <span>ユーザー管理</span>
        </NavLink>

        <NavLink
          to="/incidents"
          className={({ isActive }) =>
            isActive
              ? "sidebar-link active"
              : "sidebar-link"
          }
        >
          <span className="sidebar-icon">⚠️</span>
          <span>異常対応入力</span>
        </NavLink>

        <NavLink
          to="/statistics"
          className={({ isActive }) =>
            isActive
              ? "sidebar-link active"
              : "sidebar-link"
          }
        >
          <span className="sidebar-icon">📊</span>
          <span>データ分析</span>
        </NavLink>

      </nav>

      <div className="sidebar-footer">

        <div className="login-user">
          <div className="login-user-icon">
            管
          </div>

          <div>
            <p className="login-user-name">
              システム管理者
            </p>

            <p className="login-user-detail">
              プレス部
            </p>
          </div>
        </div>

        <button className="logout-button">
          ログアウト
        </button>

      </div>

    </aside>
  );
}

export default Sidebar;