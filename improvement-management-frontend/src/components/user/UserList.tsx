import { useState } from "react";
import type { User } from "../../types/user";
import { deleteUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import ConfirmDialog from "../common/ConfirmDialog";
import { useToast } from "../../hooks/common/useToast";
import { useNavigate } from "react-router-dom";
import { ROLE } from "../../constants/role";

import "../../styles/user/UserList.css";

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
  const navigate = useNavigate();

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
    <div className="user-page-header">
      <div>
        <h1>ユーザー管理</h1>
        <p>登録されているユーザーの一覧</p>
      </div>

      <button
        className="create-user-button"
        onClick={() => navigate("/users/new")}
      >＋ 新規ユーザー作成
      </button>
    </div>
      <div className="user-list-card">
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
                  <span className="self-label">操作不可</span>
                  )}
                  </div>
              </td>
              </tr>
              );
            })}
        </tbody>
      </table>
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
          <div>
            <p>名前：{targetUser.name}</p>
            <p>部署：{targetUser.departmentName}</p>
            <p>役職：{targetUser.roleName}</p>
          </div>
        )}
      </ConfirmDialog>
    </>
  );
}

export default UserList;