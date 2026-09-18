import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import UserUpdateForm from "../../components/user/UserUpdateForm";

import type { UserDetail } from "../../types/user";
import type { Department, Role } from "../../types/master";
import { getDepartments, getRoles } from "../../api/master/masterApi";
import { getUserById } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

import { UserPen } from "lucide-react";

function UserUpdatePage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [user, setUser] = useState<UserDetail | null>(null);
  const [departments, setDepartments] = useState<Department[]>([]);
  const [roles, setRoles] = useState<Role[]>([]);
  const handleApiError = useApiErrorHandler();

useEffect(() => {
  const fetchData = async () => {
    if (!id) {
      return;
    }

    try {
      const [userData, departmentsData, rolesData] = await Promise.all([
        getUserById(Number(id)),
        getDepartments(),
        getRoles(),
      ]);

      setUser(userData);
      setDepartments(departmentsData);
      setRoles(rolesData);
    } catch (error) {
      handleApiError(error);
    }
  };

  fetchData();
}, [id, handleApiError]);

  const handleUpdated = () => {
    navigate("/users");
  };

return (
  <main>
    <div className="page-container">

      <div className="page-header">
      <div className="page-header-icon">
        <UserPen />
      </div>

        <div>
          <h1>ユーザー更新</h1>
          <p>ユーザー情報を編集します</p>
        </div>
      </div>

      <UserUpdateForm
        user={user}
        onUpdated={handleUpdated}
        departments={departments}
        roles={roles}
      />

    </div>
  </main>
);
}

export default UserUpdatePage;