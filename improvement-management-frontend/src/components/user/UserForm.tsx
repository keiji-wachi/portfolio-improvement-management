import { useState } from "react";
import type { Department, Role } from "../../types/master";
import { createUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

type Props = {
    onCreated: () => void;
    departments : Department[];
    roles : Role[];
};

function UserCreateForm({ onCreated, departments, roles }:Props){
    const [name, setName] = useState("");
    const [departmentId, setDepartmentId] = useState("");
    const [roleId, setRoleId] = useState("");
    const [password, setPassword] = useState("");
    const [employeeNo, setEmployeeNo] = useState("");
    const handleApiError = useApiErrorHandler();

    const userCreate = async () => {
    try{
        await createUser({
            employeeNo,
            name,
            departmentId: Number(departmentId),
            roleId: Number(roleId),
            password,
        });

        onCreated();
    } catch (error) {
        handleApiError(error);
    }
};

    return (
        <div>
            <h2>ユーザー登録</h2>

            <div>
                <label>社員番号</label>
                <input type="text" value={employeeNo} onChange={(e) => setEmployeeNo(e.target.value)} placeholder="社員番号"/>
            </div>

            <div>
                <label>名前</label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)}/>
            </div>

            <div>
                <label>部署</label>
                <select value={departmentId} onChange={(e) => setDepartmentId(e.target.value)}>
                <option value="">部署を選択</option>

                {departments.map((department) => (
                    <option key={department.departmentId} value={department.departmentId}>{department.departmentName}</option> 
                ))}
                
                </select>
            </div>            

            <div>
                <label>役職</label>
                <select value={roleId} onChange={(e) => setRoleId(e.target.value)}>
                <option value="">役職を選択</option>   

                {roles.map((role) => (
                    <option key={role.roleId} value={role.roleId}>{role.roleName}</option>
                ))}

                </select>
            </div>

            <div>
                <label>パスワード</label>
                <input type="text" value={password} onChange={(e) => setPassword(e.target.value)}/>
            </div>

            <button onClick={userCreate}>登録</button>
        </div>
    );
}

export default UserCreateForm;