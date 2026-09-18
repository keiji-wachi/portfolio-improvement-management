import { NavLink } from "react-router-dom";
import { useAuth } from "../../hooks/auth/useAuth";
import {
  Factory,
  Users,
  TriangleAlert,
  ChartNoAxesCombined,
  LogOut,
} from "lucide-react";

import "../../styles/common/Sidebar.css";

function Sidebar() {
  const { loginUser } = useAuth();

  return (
    <aside className="sidebar">

<div className="sidebar-header">
  <div className="sidebar-brand">
    <Factory size={24} />

    <div>
      <h2>改善管理システム</h2>
      <p>管理パネル</p>
    </div>
  </div>
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
  <Users className="sidebar-icon" size={20} />
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
          <TriangleAlert className="sidebar-icon" size={20} />
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
          <ChartNoAxesCombined className="sidebar-icon" size={20} />
            <span>データ分析</span>
        </NavLink>

      </nav>

      <div className="sidebar-footer">

        <div className="login-user">

          <div className="login-user-icon">
            {loginUser?.name?.charAt(0)}
          </div>

          <div>
            <p className="login-user-name">
              {loginUser?.name}
            </p>

            <p className="login-user-detail">
              {loginUser?.departmentName}
            </p>
          </div>

        </div>

        <button className="logout-button">
          <LogOut size={18} />
            <span>ログアウト</span>
        </button>
      </div>
    </aside>
  );
}

export default Sidebar;