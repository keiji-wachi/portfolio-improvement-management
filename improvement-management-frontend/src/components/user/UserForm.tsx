import { useState } from "react";

import type { Department, Role } from "../../types/master";

import { createUser } from "../../api/user/userApi";
import { ApiError } from "../../api/client";

import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { useToast } from "../../hooks/common/useToast";

import ErrorMessage from "../common/ErrorMessage";
import ConfirmDialog from "../common/ConfirmDialog";


type Props = {
  onCreated: () => void;
  departments: Department[];
  roles: Role[];
};

function UserCreateForm({
  onCreated,
  departments,
  roles,
}: Props) {

  const [employeeNo, setEmployeeNo] = useState("");
  const [name, setName] = useState("");
  const [departmentId, setDepartmentId] = useState("");
  const [roleId, setRoleId] = useState("");
  const [password, setPassword] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);

  const [errors, setErrors] = useState<{
    employeeNo?: string;
    name?: string;
    departmentId?: string;
    roleId?: string;
    password?: string;
  }>({});

  const handleApiError = useApiErrorHandler();
  const { showToast } = useToast();

  const validate = () => {
    const newErrors: {
      employeeNo?: string;
      name?: string;
      departmentId?: string;
      roleId?: string;
      password?: string;
    } = {};

    if (!employeeNo.trim()) {
      newErrors.employeeNo =
        "社員番号を入力してください";
    }

    if (!name.trim()) {
      newErrors.name =
        "名前を入力してください";
    }

    if (!departmentId) {
      newErrors.departmentId =
        "部署を選択してください";
    }

    if (!roleId) {
      newErrors.roleId =
        "役職を選択してください";
    }

    if (!password) {
      newErrors.password =
        "パスワードを入力してください";
    }

    setErrors(newErrors);

    return Object.keys(newErrors).length === 0;
  };

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

  const selectedDepartment = departments.find(
    (department) =>
      department.departmentId === Number(departmentId)
  );

  const selectedRole = roles.find(
    (role) =>
      role.roleId === Number(roleId)
  );

  const userCreate = async () => {
    setErrorMessage("");
    setIsLoading(true);

    try {
      await createUser({
        employeeNo,
        name,
        departmentId: Number(departmentId),
        roleId: Number(roleId),
        password,
      });

      setIsConfirmOpen(false);

      showToast("ユーザーを登録しました");

      onCreated();

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
        <h2>ユーザー情報</h2>
      </div>

      <div className="form-card-body">

        <ErrorMessage message={errorMessage} />

        <form onSubmit={handleConfirm}>

          <div className="form-group">
            <label>
              社員番号
              <span className="required-mark">*</span>
            </label>

            <input
              type="text"
              value={employeeNo}
              onChange={(e) =>
                setEmployeeNo(e.target.value)
              }
              placeholder="社員番号を入力してください"
            />

            {errors.employeeNo && (
              <p className="field-error">
                {errors.employeeNo}
              </p>
            )}
          </div>

          <div className="form-group">
            <label>
              名前
              <span className="required-mark">*</span>
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
              <span className="required-mark">*</span>
            </label>

            <select
              value={departmentId}
              onChange={(e) =>
                setDepartmentId(e.target.value)
              }
            >
              <option value="">
                部署を選択してください
              </option>

              {departments.map((department) => (
                <option
                  key={department.departmentId}
                  value={department.departmentId}
                >
                  {department.departmentName}
                </option>
              ))}
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
              <span className="required-mark">*</span>
            </label>

            <select
              value={roleId}
              onChange={(e) =>
                setRoleId(e.target.value)
              }
            >
              <option value="">
                役職を選択してください
              </option>

              {roles.map((role) => (
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

          <div className="form-group">
            <label>
              パスワード
              <span className="required-mark">*</span>
            </label>

            <input
              type="password"
              value={password}
              onChange={(e) =>
                setPassword(e.target.value)
              }
              placeholder="パスワードを入力してください"
            />

            {errors.password && (
              <p className="field-error">
                {errors.password}
              </p>
            )}
          </div>

          <div className="form-actions">
            <button
              className="btn-primary"
              type="submit"
              disabled={isLoading}
            >
              登録内容を確認
            </button>
          </div>

        </form>
      </div>
    </div>

    <ConfirmDialog
      open={isConfirmOpen}
      title="ユーザー登録確認"
      message="以下の内容でユーザーを登録しますか？"
      confirmText="登録する"
      cancelText="キャンセル"
      isLoading={isLoading}
      onConfirm={userCreate}
      onCancel={() => setIsConfirmOpen(false)}
    >
      <div className="confirm-detail-list">

        <div className="confirm-detail-row">
          <span className="confirm-detail-label">
            社員番号
          </span>
          <span className="confirm-detail-value">
            {employeeNo}
          </span>
        </div>

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
            {selectedDepartment?.departmentName}
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

export default UserCreateForm;