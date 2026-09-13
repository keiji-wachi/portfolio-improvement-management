import { useEffect, useState } from "react";
import type { User } from "../../types/user";
import type { Department, Role } from "../../types/master";
import { updateUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

type Props = {
  onUpdated: () => void;
  user: User | null;
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

  try{
  await updateUser(user.id, {
    name,
    departmentId,
    roleId,
  });

  onUpdated();
  } catch (error){
    handleApiError(error);
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

      <button onClick={userUpdate}>変更</button>
    </div>
  );
}

export default UserUpdateForm;