import { Outlet } from "react-router-dom";
import Sidebar from "../components/common/Sidebar";
import "../styles/common/AppLayout.css";

function AppLayout() {
  return (
    <div className="app-layout">

      <Sidebar />

      <main className="main-content">
        <Outlet />
      </main>

    </div>
  );
}

export default AppLayout;