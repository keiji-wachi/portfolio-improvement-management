import { useState } from "react";
import type { User } from "../../types/user";
import { deleteUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import ConfirmDialog from "../common/ConfirmDialog";
import { useToast } from "../../hooks/common/useToast";

type Props = {
  users: User[];
  onEdit: (user: User) => void;
  onDeleted: () => void;
};

function UserList({ users, onEdit, onDeleted }: Props) {
  const [targetUser, setTargetUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleApiError = useApiErrorHandler();
  const { showToast } = useToast();

  const handleDelete = async () => {
    if (!targetUser) return;

    setIsLoading(true);

    try {
      await deleteUser(targetUser.id);

      setTargetUser(null);

      showToast("ユーザーを削除しました");

      onDeleted();
    } catch (error) {
      handleApiError(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      {users.map((user) => (
        <div key={user.id}>
          名前：{user.name}
          部署：{user.departmentName}
          役職：{user.roleName}

          <button
            onClick={() => setTargetUser(user)}
            disabled={isLoading}
          >
            削除
          </button>

          <button onClick={() => onEdit(user)}>
            編集
          </button>
        </div>
      ))}

      <ConfirmDialog
        open={targetUser !== null}
        title="ユーザー削除確認"
        message="以下のユーザーを削除しますか？"
        confirmText="削除する"
        cancelText="キャンセル"
        isLoading={isLoading}
        onConfirm={handleDelete}
        onCancel={() => setTargetUser(null)}
      >
        {targetUser && (
          <div>
            <p>名前：{targetUser.name}</p>
            <p>部署：{targetUser.departmentName}</p>
            <p>役職：{targetUser.roleName}</p>
          </div>
        )}
      </ConfirmDialog>
    </div>
  );
}

export default UserList;