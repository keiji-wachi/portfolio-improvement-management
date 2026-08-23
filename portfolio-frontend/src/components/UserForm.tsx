import { useState } from "react";
import type { Department, Role } from "../types/master";

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

    const userCreate = async () => {
    const response = await fetch(`http://localhost:8080/users`,{
        method:"POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },

        body: JSON.stringify({
            employeeNo: employeeNo,
            name: name,
            department_id: departmentId,
            role_id: roleId,
            password: password,
        }),
      });

    const result = await response.text();
    console.log(result);
    onCreated();
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