import { useState } from "react";
import type { User } from "./UseList";
import { useEffect } from "react";
import type { Department, Role } from "../types/master";

type Props = {
    onCreated: () => void;
    user: User | null;
    departments : Department[];
    roles : Role[];
};

//①onCreated：リファクタリング
function UserUpdateForm({ user, onCreated, departments, roles }:Props){
    const [name, setName] = useState("");
    const [departmentId, setDepartmentId] = useState<number>(0);
    const [roleId, setRoleId] = useState<number>(0);

    useEffect(() => {
        if(user) {
            setName(user.name);
            setDepartmentId(user.departmentId);
            setRoleId(user.roleId);
        }
    }, [user]);

    const userUpdate = async () => {

    if(!user) return;

    await fetch(`http://localhost:8080/users/${user.id}`,{
        method:"PUT",
        credentials: "include",
        headers: {
            "Content-Type": "application/json",
        },

        body: JSON.stringify({
            name: name,
            departmentId: departmentId,
            roleId: roleId,
        }),
      });
    onCreated();
  };
    return (
        <div>
            <h2>編集フォーム</h2>

            <div>
                <label>名前</label>
                <input type="text" value={name} onChange={(e) => setName(e.target.value)}/>
            </div>

            <div>
                <label>部署</label>
                <select value={departmentId} onChange={(e) => setDepartmentId(Number(e.target.value))}>
                <option value="">部署を選択</option>

                {departments.map((department) => (
                    <option key={department.departmentId} value={department.departmentId}>{department.departmentName}</option> 
                ))}
                
                </select>
            </div>            

            <div>
                <label>役職</label>
                <select value={roleId} onChange={(e) => setRoleId(Number(e.target.value))}>
                <option value="">役職を選択</option>   

                {roles.map((role) => (
                    <option key={role.roleId} value={role.roleId}>{role.roleName}</option>
                ))}

                </select>
            </div>

            <button onClick={userUpdate}>変更</button>
        </div>
    );
}

export default UserUpdateForm;