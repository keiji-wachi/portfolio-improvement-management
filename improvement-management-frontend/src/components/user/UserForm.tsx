import { useState } from "react";
import type { Department, Role } from "../../types/master";
import { createUser } from "../../api/user/userApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { ApiError } from "../../api/client";
import ErrorMessage from "../common/ErrorMessage";
import ConfirmDialog from "../common/ConfirmDialog";
import { useToast } from "../../hooks/common/useToast";

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
    const [isLoading, setIsLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState("");
    const [isConfirmOpen, setIsConfirmOpen] = useState(false);
    const { showToast } = useToast();

    const handleApiError = useApiErrorHandler();

    const userCreate = async () => {
        setErrorMessage("");
        setIsLoading(true);
    try{
        await createUser({
            employeeNo,
            name,
            departmentId: Number(departmentId),
            roleId: Number(roleId),
            password,
        });
        setIsConfirmOpen(false)
        showToast("ユーザーを登録しました");
        onCreated();
    } catch (error) {
        if (error instanceof ApiError && error.status === 400) {
            setErrorMessage(error.message);
            setIsConfirmOpen(false)
            return;
        }

        handleApiError(error);

    } finally {
        setIsLoading(false);
    }
};

    return (
        <div>
            <h2>ユーザー登録</h2>
                <ErrorMessage message={errorMessage} />

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

            <button
                onClick={() => setIsConfirmOpen(true)}
                disabled={isLoading}
                >登録
            </button>

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
                <div>
                    <p>社員番号：{employeeNo}</p>
                    <p>名前：{name}</p>
                    <p>部署：{departments.find((department) =>department.departmentId === Number(departmentId))?.departmentName}</p>
                    <p>役職：{roles.find((role) =>role.roleId === Number(roleId))?.roleName}</p>
                </div>
            </ConfirmDialog>
        </div>
    );
}

export default UserCreateForm;