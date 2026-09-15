import { useEffect, useState } from "react";
import type { UserDetail } from "../../types/user";
import type { Department, Role } from "../../types/master";
import { updateUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import ConfirmDialog from "../common/ConfirmDialog";
import { useToast } from "../../hooks/common/useToast";
import { ApiError } from "../../api/client";
import ErrorMessage from "../common/ErrorMessage";

type Props = {
  onUpdated: () => void;
  user: UserDetail | null;
  departments: Department[];
  roles: Role[];
};

function UserUpdateForm({
  user,
  onUpdated,
  departments,
  roles,
}: Props) {

  const [name, setName] = useState("");
  const [departmentId, setDepartmentId] = useState<number>(0);
  const [roleId, setRoleId] = useState<number>(0);

  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const { showToast } = useToast();
  const [errorMessage, setErrorMessage] = useState("");

  const handleApiError = useApiErrorHandler();

  useEffect(() => {
    if (user) {
      setName(user.name);
      setDepartmentId(user.departmentId);
      setRoleId(user.roleId);
    }
  }, [user]);

const userUpdate = async () => {
  if (!user) return;
  setErrorMessage("");
  setIsLoading(true);

  try {
    await updateUser(user.id, {
      name,
      departmentId,
      roleId,
    });

    setIsConfirmOpen(false);

    showToast("ユーザーを更新しました");

    onUpdated();
  } catch (error) {
    if (error instanceof ApiError && error.status === 400) {
      setErrorMessage(error.message);
      handleApiError(error);
      return;
    }
  } finally {
    setIsLoading(false);
  }
};

  return (
    <div>
      <h2>編集フォーム</h2>

      <div>
        <label>名前</label>
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
      </div>

      <div>
        <label>部署</label>
        <select
          value={departmentId}
          onChange={(e) => setDepartmentId(Number(e.target.value))}
        >
          <option value="">部署を選択</option>

          {departments.map((department) => (
            <option
              key={department.departmentId}
              value={department.departmentId}
            >
              {department.departmentName}
            </option>
          ))}
        </select>
      </div>

      <div>
        <label>役職</label>
        <select
          value={roleId}
          onChange={(e) => setRoleId(Number(e.target.value))}
        >
          <option value="">役職を選択</option>

          {roles.map((role) => (
            <option key={role.roleId} value={role.roleId}>
              {role.roleName}
            </option>
          ))}
        </select>
      </div>

      <button onClick={() => setIsConfirmOpen(true)}>
        更新
      </button>

      <ConfirmDialog
        open={isConfirmOpen}
        title="ユーザー更新確認"
        confirmText="更新する"
        cancelText="キャンセル"
        isLoading={isLoading}
        onConfirm={userUpdate}
        onCancel={() => setIsConfirmOpen(false)}
      >
      <div>
        <p>以下の内容で更新します。</p>

        <p>名前：{name}</p>

      <p>
        部署：
          {
            departments.find(
            (department) => department.departmentId === departmentId
            )?.departmentName
          }
      </p>

      <p>
        役職：
          {
            roles.find(
            (role) => role.roleId === roleId
            )?.roleName
          }
      </p>
      </div>
      </ConfirmDialog>
    </div>
  );
}

export default UserUpdateForm;