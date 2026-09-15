import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import UserUpdateForm from "../../components/user/UserUpdateForm";

import type { UserDetail } from "../../types/user";
import type { Department, Role } from "../../types/master";
import { getDepartments, getRoles } from "../../api/master/masterApi";
import { getUserById } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

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
      <h1>ユーザー更新</h1>

      <UserUpdateForm
        user={user}
        onUpdated={handleUpdated}
        departments={departments}
        roles={roles}
      />
    </main>
  );
}

export default UserUpdatePage;