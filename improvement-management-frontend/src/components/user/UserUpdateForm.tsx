import { useEffect, useState } from "react";

import type { UserDetail } from "../../types/user";
import type { Department, Role } from "../../types/master";

import { updateUser } from "../../api/user/userApi";
import { ApiError } from "../../api/client";

import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { useToast } from "../../hooks/common/useToast";
import { useAuth } from "../../hooks/auth/useAuth";

import ConfirmDialog from "../common/ConfirmDialog";
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

  const { loginUser } = useAuth();

  // ロールID
  const SYSTEM_ADMIN = 1;
  const INSTRUCTOR = 2;
  const RELIEF = 3;
  const WORKER = 4;

  // 入力値
  const [name, setName] = useState("");
  const [departmentId, setDepartmentId] = useState(0);
  const [roleId, setRoleId] = useState(0);

  // 画面状態
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  // 入力エラー
  const [errors, setErrors] = useState<{
    name?: string;
    departmentId?: string;
    roleId?: string;
  }>({});

  const { showToast } = useToast();
  const handleApiError = useApiErrorHandler();

  const isSystemAdmin =
    loginUser?.roleId === SYSTEM_ADMIN;

  const isInstructor =
    loginUser?.roleId === INSTRUCTOR;

  // ログインユーザーの権限に応じて部署候補を制限
  const selectableDepartments = isSystemAdmin
    ? departments
    : isInstructor
      ? departments.filter(
          (department) =>
            department.departmentId ===
            loginUser?.departmentId
        )
      : [];

  // ログインユーザーの権限に応じて役職候補を制限
  const selectableRoles = isSystemAdmin
    ? roles
    : isInstructor
      ? roles.filter(
          (role) =>
            role.roleId === RELIEF ||
            role.roleId === WORKER
        )
      : [];

  // 更新対象ユーザーをフォームへ反映
  useEffect(() => {
    if (user) {
      setName(user.name);
      setDepartmentId(user.departmentId);
      setRoleId(user.roleId);
    }
  }, [user]);

  // 入力チェック
  const validate = () => {
    const newErrors: {
      name?: string;
      departmentId?: string;
      roleId?: string;
    } = {};

    if (!name.trim()) {
      newErrors.name = "名前を入力してください";
    }

    if (departmentId === 0) {
      newErrors.departmentId =
        "部署を選択してください";
    }

    if (roleId === 0) {
      newErrors.roleId =
        "役職を選択してください";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

  // 確認ダイアログを開く
  const handleConfirm = (
    e: React.FormEvent<HTMLFormElement>
  ) => {
    e.preventDefault();

    setErrorMessage("");

    if (!validate()) {
      return;
    }

    setIsConfirmOpen(true);
  };

  // 確認画面表示用
  const selectedDepartment = departments.find(
    (department) =>
      department.departmentId === departmentId
  );

  const selectedRole = roles.find(
    (role) =>
      role.roleId === roleId
  );

  // ユーザー更新
  const userUpdate = async () => {
    if (!user) {
      return;
    }

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

      if (
        error instanceof ApiError &&
        error.status === 400
      ) {
        setErrorMessage(error.message);
        setIsConfirmOpen(false);
        return;
      }

      handleApiError(error);

    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <div className="form-card">

        <div className="form-card-header">
          <h2>編集内容</h2>
        </div>

        <div className="form-card-body">

          <ErrorMessage message={errorMessage} />

          <form onSubmit={handleConfirm}>

            <div className="form-group">
              <label>
                名前
                <span className="required-mark">
                  *
                </span>
              </label>

              <input
                type="text"
                value={name}
                onChange={(e) =>
                  setName(e.target.value)
                }
                placeholder="名前を入力してください"
              />

              {errors.name && (
                <p className="field-error">
                  {errors.name}
                </p>
              )}
            </div>

            <div className="form-group">
              <label>
                部署
                <span className="required-mark">
                  *
                </span>
              </label>

              <select
                value={departmentId}
                onChange={(e) =>
                  setDepartmentId(
                    Number(e.target.value)
                  )
                }
                disabled={isInstructor}
              >
                <option value={0}>
                  部署を選択してください
                </option>

                {selectableDepartments.map(
                  (department) => (
                    <option
                      key={
                        department.departmentId
                      }
                      value={
                        department.departmentId
                      }
                    >
                      {
                        department.departmentName
                      }
                    </option>
                  )
                )}
              </select>

              {errors.departmentId && (
                <p className="field-error">
                  {errors.departmentId}
                </p>
              )}
            </div>

            <div className="form-group">
              <label>
                役職
                <span className="required-mark">
                  *
                </span>
              </label>

              <select
                value={roleId}
                onChange={(e) =>
                  setRoleId(
                    Number(e.target.value)
                  )
                }
              >
                <option value={0}>
                  役職を選択してください
                </option>

                {selectableRoles.map((role) => (
                  <option
                    key={role.roleId}
                    value={role.roleId}
                  >
                    {role.roleName}
                  </option>
                ))}
              </select>

              {errors.roleId && (
                <p className="field-error">
                  {errors.roleId}
                </p>
              )}
            </div>

            <div className="form-actions">
              <button
                className="btn-primary"
                type="submit"
                disabled={
                  isLoading || !user
                }
              >
                更新内容を確認
              </button>
            </div>

          </form>

        </div>
      </div>

      <ConfirmDialog
        open={isConfirmOpen}
        title="ユーザー更新確認"
        message="以下の内容でユーザー情報を更新しますか？"
        confirmText="更新する"
        cancelText="キャンセル"
        isLoading={isLoading}
        onConfirm={userUpdate}
        onCancel={() =>
          setIsConfirmOpen(false)
        }
      >
        <div className="confirm-detail-list">

          <div className="confirm-detail-row">
            <span className="confirm-detail-label">
              名前
            </span>

            <span className="confirm-detail-value">
              {name}
            </span>
          </div>

          <div className="confirm-detail-row">
            <span className="confirm-detail-label">
              部署
            </span>

            <span className="confirm-detail-value">
              {
                selectedDepartment
                  ?.departmentName
              }
            </span>
          </div>

          <div className="confirm-detail-row">
            <span className="confirm-detail-label">
              役職
            </span>

            <span className="confirm-detail-value">
              {selectedRole?.roleName}
            </span>
          </div>

        </div>
      </ConfirmDialog>
    </>
  );
}

export default UserUpdateForm;