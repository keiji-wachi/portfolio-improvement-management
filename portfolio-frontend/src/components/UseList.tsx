import { useEffect, useState } from "react";

export type User = {
  id: number;
  name: string;
  departmentId: number;
  roleId: number;
  departmentName: String;
  roleName: String;
}

type Props = {
    reloadkey: number;
    onEdit: (user: User) => void;
};

function UserList({ reloadkey, onEdit}: Props){
    
    const [ users, setUsers] = useState<User[]>([]);

    const fetchUsers = async () => {
        const response = await fetch("http://localhost:8080/users",{
            credentials: "include"
        });
        const data = await response.json();
        setUsers(data);
    };

    const deleteUsers = async (id:number) =>{
        await fetch(`http://localhost:8080/users/${id}`,{
            method: "DELETE",
            credentials: "include"
        });

        fetchUsers();
    };

    useEffect(() => {
        fetchUsers();
    }, [reloadkey]);

    return (
        <div> 
        {users.map((users)=>(
            <div key={users.id}>
                名前：{users.name}
                部署:{users.departmentName}
                役職:{users.roleName}

                <button onClick={() => deleteUsers(users.id)}>
                    削除
                </button>

                <button onClick={() => onEdit(users)}>
                    編集
                </button>

            </div>
            
      ))}
        </div>
    );
    
    
}

export default UserList;