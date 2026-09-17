import {
  BrowserRouter,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";

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
import AppLayout from "../layouts/AppLayout";

function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>

        <Route
          path="/"
          element={<Navigate to="/login" replace />}
        />

        <Route
          path="/login"
          element={<LoginPage />}
        />

        <Route
          element={
            <ProtectedRoute>
              <AppLayout />
            </ProtectedRoute>
          }
        >
          <Route
            path="/users"
            element={<UserListPage />}
          />

          <Route
            path="/users/new"
            element={<UserCreatePage />}
          />

          <Route
            path="/users/:id/edit"
            element={<UserUpdatePage />}
          />

          <Route
            path="/incidents"
            element={<IncidentPage />}
          />

          <Route
            path="/statistics"
            element={<StatisticsPage />}
          />
        </Route>

        <Route
          path="/403"
          element={<ForbiddenPage />}
        />

        <Route
          path="/500"
          element={<ServerErrorPage />}
        />

        <Route
          path="/404"
          element={<NotFoundPage />}
        />

        <Route
          path="*"
          element={<NotFoundPage />}
        />

      </Routes>
    </BrowserRouter>
  );
}

export default AppRouter;