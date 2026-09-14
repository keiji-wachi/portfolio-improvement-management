import { BrowserRouter, Route, Routes, Navigate  } from "react-router-dom";

import LoginPage from "../pages/auth/LoginPage";
import UserListPage from "../pages/user/UserListPage";
import UserCreatePage from "../pages/user/UserCreatePage";
import UserUpdatePage from "../pages/user/UserUpdatePage";
import IncidentPage from "../pages/incident/IncidentPage";
import StatisticsPage from "../pages/statistics/StatisticsPage";

import ForbiddenPage from "../pages/error/ForbiddenPage";
import NotFoundPage from "../pages/error/NotFoundPage";
import ServerErrorPage from "../pages/error/ServerErrorPage";

import ProtectedRoute from "./ProtectedRoute";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/users" element={<ProtectedRoute><UserListPage /></ProtectedRoute>} />
        <Route path="/users/new" element={<ProtectedRoute><UserCreatePage /></ProtectedRoute>} />
        <Route path="/users/:id/edit" element={<ProtectedRoute><UserUpdatePage /></ProtectedRoute>} />
        <Route path="/incidents" element={<ProtectedRoute><IncidentPage /></ProtectedRoute>} />
        <Route path="/statistics" element={<ProtectedRoute><StatisticsPage /></ProtectedRoute>} />

        <Route path="/403" element={<ForbiddenPage />} />
        <Route path="/500" element={<ServerErrorPage />} />
        <Route path="/404" element={<NotFoundPage />} />
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;