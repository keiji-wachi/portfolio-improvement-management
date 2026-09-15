import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import UserList from "../../components/user/UserList";
import { getUsers } from "../../api/user/userApi";
import type { User } from "../../types/user";

import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

function UserListPage() {
  const [users, setUsers] = useState<User[]>([]);
  const navigate = useNavigate();
  const handleApiError = useApiErrorHandler();

  const fetchUsers = async () => {
    try{
    const data = await getUsers();
    setUsers(data);
    } catch (error){
      handleApiError(error)
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  const handleEdit = (user: User) => {
    navigate(`/users/${user.id}/edit`);
  };

  return (
    <main>
      <h1>ユーザー一覧</h1>

      <UserList
        users={users}
        onEdit={handleEdit}
        onDeleted={fetchUsers}
      />
    </main>
  );
}

export default UserListPage;