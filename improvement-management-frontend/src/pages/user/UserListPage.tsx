import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import UserList from "../../components/user/UserList";
import { getUsers } from "../../api/user/userApi";
import type { User } from "../../types/user";

import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { AuthContext } from "../../context/auth/AuthContext";

function UserListPage() {
  const [users, setUsers] = useState<User[]>([]);
  const navigate = useNavigate();
  const handleApiError = useApiErrorHandler();

  const auth = useContext(AuthContext);

  const fetchUsers = async () => {
    try {
      const data = await getUsers();
      setUsers(data);
    } catch (error) {
      handleApiError(error);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleEdit = (user: User) => {
    navigate(`/users/${user.id}/edit`);
  };

  console.log("loginUser:", auth?.loginUser);

if (auth?.loginUser) {
  console.log("loginUser keys:", Object.keys(auth.loginUser));
}

  if (!auth || !auth.loginUser) {
    return null;
  }

  console.log("loginUser:", auth.loginUser);


  return (
    <main>
      <UserList
        users={users}
        loginUserId={auth.loginUser.userId}
        loginUserRoleId={auth.loginUser.roleId}
        onEdit={handleEdit}
        onDeleted={fetchUsers}
      />
    </main>
  );
}

export default UserListPage;