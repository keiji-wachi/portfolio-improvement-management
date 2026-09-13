import type { User } from "../../types/user";
import { deleteUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

type Props = {
  users: User[];
  onEdit: (user: User) => void;
  onDeleted: () => void;
};

function UserList({ users, onEdit, onDeleted }: Props) {
  const handleApiError = useApiErrorHandler();

  const handleDelete = async (id: number) => {
    try{
    await deleteUser(id);
    onDeleted();
    } catch (error){
        handleApiError(error);
    }
  };

  return (
    <div>
      {users.map((user) => (
        <div key={user.id}>
          名前：{user.name}
          部署：{user.departmentName}
          役職：{user.roleName}

          <button onClick={() => handleDelete(user.id)}>
            削除
          </button>

          <button onClick={() => onEdit(user)}>
            編集
          </button>
        </div>
      ))}
    </div>
  );
}

export default UserList;