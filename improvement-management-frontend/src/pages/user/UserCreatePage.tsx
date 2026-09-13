import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import type { Department, Role } from "../../types/master";
import { getDepartments, getRoles } from "../../api/master/masterApi";

import UserCreateForm from "../../components/user/UserForm";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

function UserCreatePage() {
  const [departments, setDepartments] = useState<Department[]>([]);
  const [roles, setRoles] = useState<Role[]>([]);

  const navigate = useNavigate();
  const handleApiError = useApiErrorHandler();

useEffect(() => {
  const fetchMasters = async () => {
    try {
      const [departmentsData, rolesData] = await Promise.all([
        getDepartments(),
        getRoles(),
      ]);

      setDepartments(departmentsData);
      setRoles(rolesData);
    } catch (error) {
      handleApiError(error);
    }
  };

  fetchMasters();
}, [handleApiError]);

const handleCreated = () => {
  navigate("/users");
};

  return (
    <main>
      <h1>ユーザー登録</h1>

      <UserCreateForm
        onCreated={handleCreated}
        departments={departments}
        roles={roles}
      />
    </main>
  );
}

export default UserCreatePage;