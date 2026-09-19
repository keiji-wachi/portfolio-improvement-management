import { useState } from "react";
import type { User } from "../../types/user";
import { deleteUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import ConfirmDialog from "../common/ConfirmDialog";
import { useToast } from "../../hooks/common/useToast";
import { ROLE } from "../../constants/role";

type Props = {
  users: User[];
  loginUserId: number;
  loginUserRoleId: number;
  onEdit: (user: User) => void;
  onDeleted: () => void;
};

function UserList({
  users,
  loginUserId,
  loginUserRoleId,
  onEdit,
  onDeleted,
}: Props) {

  const [targetUser, setTargetUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const handleApiError = useApiErrorHandler();
  const { showToast } = useToast();

const canEdit = (user: User) => {
 
  if (user.id === loginUserId) {
    return false;
  }

 
  if (loginUserRoleId === ROLE.SYSTEM_ADMIN) {
    return true;
  }


  if (loginUserRoleId === ROLE.INSTRUCTOR) {
    return (
      user.roleId === ROLE.RELIEF ||
      user.roleId === ROLE.WORKER
    );
  }

  return false;
};

const canDelete = (user: User) => {

  if (user.id === loginUserId) {
    return false;
  }


  if (loginUserRoleId === ROLE.SYSTEM_ADMIN) {
    return true;
  }


  if (loginUserRoleId === ROLE.INSTRUCTOR) {
    return (
      user.roleId === ROLE.RELIEF ||
      user.roleId === ROLE.WORKER
    );
  }

  return false;
};

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
  <>
    <div className="user-list-card">

      <div className="user-list-card-header">
        <h2>登録ユーザー一覧</h2>

        <span className="user-list-count">
          {users.length}件
        </span>
      </div>

      <div className="user-list-table-wrapper">
        <table className="user-table">
          <thead>
            <tr>
              <th>氏名</th>
              <th>部署</th>
              <th>ロール</th>
              <th>アクション</th>
            </tr>
          </thead>

          <tbody>
            {users.map((user) => {
              const editable = canEdit(user);
              const deletable = canDelete(user);

              return (
                <tr key={user.id}>
                  <td>{user.name}</td>
                  <td>{user.departmentName}</td>
                  <td>{user.roleName}</td>

                  <td>
                    <div className="user-actions">

                      {editable && (
                        <button
                          className="edit-button"
                          onClick={() => onEdit(user)}
                        >
                          編集
                        </button>
                      )}

                      {deletable && (
                        <button
                          className="delete-button"
                          onClick={() => setTargetUser(user)}
                          disabled={isLoading}
                        >
                          削除
                        </button>
                      )}

                      {!editable && !deletable && (
                        <span className="self-label">
                          操作不可
                        </span>
                      )}

                    </div>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>

    </div>

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
        <div className="confirm-detail-list">

          <div className="confirm-detail-row">
            <span className="confirm-detail-label">
              名前
            </span>

            <span className="confirm-detail-value">
              {targetUser.name}
            </span>
          </div>

          <div className="confirm-detail-row">
            <span className="confirm-detail-label">
              部署
            </span>

            <span className="confirm-detail-value">
              {targetUser.departmentName}
            </span>
          </div>

          <div className="confirm-detail-row">
            <span className="confirm-detail-label">
              役職
            </span>

            <span className="confirm-detail-value">
              {targetUser.roleName}
            </span>
          </div>

        </div>
      )}
    </ConfirmDialog>
  </>
);
}

export default UserList;