import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

import UserList from "../../components/user/UserList";
import { getUsers } from "../../api/user/userApi";
import type { User } from "../../types/user";

import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { AuthContext } from "../../context/auth/AuthContext";

import { Users } from "lucide-react";

import "../../styles/user/UserList.css";

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
    <div className="page-container-wide">

      <div className="page-header page-header-with-action">

        <div className="page-header-main">
          <div className="page-header-icon">
            <Users/>
          </div>

          <div>
            <h1>ユーザー管理</h1>
            <p>登録されているユーザーの一覧</p>
          </div>
        </div>

        <button
          className="btn-primary"
          onClick={() => navigate("/users/new")}
        >
          ＋ 新規ユーザー作成
        </button>

      </div>

      <UserList
        users={users}
        loginUserId={auth.loginUser.userId}
        loginUserRoleId={auth.loginUser.roleId}
        onEdit={handleEdit}
        onDeleted={fetchUsers}
      />

    </div>
  </main>
);
}

export default UserListPage;