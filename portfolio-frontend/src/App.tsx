//import { useState } from "react";
import UserList, { type User } from "./components/UseList";
import UserCreateForm from "./components/UserForm";
import { useState, useEffect } from "react";
import type { Department, Role, LoginUser } from "./types/master";
import UserUpdateForm from "./components/UserUpdateForm";
import IncidentReportForm from "./components/IncidentReportForm";
import LoginForm from "./components/LoginForm";
import IncidentReportList from "./components/IncidentReport";

/*demoCRUD
type List = {
  id: number;
  name: string;
}*/

function App() {
  /*demoCRUD

  const [name, setName] = useState("");
  const [lists, setLists] = useState<List[]>([]);
  const [updatename, updatesetName] = useState("");

  const readAll = async () =>{
    const response = await fetch("http://localhost:8080/api/read");
    const data = await response.json();

    setLists(data);
  }
  const create = async () => {
    const response = await fetch(`http://localhost:8080/api/create?name=${encodeURIComponent(name)}`,
      {
        method:"POST",
      }
    );

    const result = await response.text();
    console.log(result);
  };

  const handledelete = async (id: number) =>{
      const response = await fetch(`http://localhost:8080/api/delete?id=${id}`,
        {
          method:"DELETE"
        }
      );

      const result = await response.text();
      console.log(result);
  }

  const update = async (id: number) =>{
    const response = await fetch(`http://localhost:8080/api/update?id=${id}&name=${encodeURIComponent(updatename)}`,
      {
        method:"PUT"
      }
    );

      const result = await response.text();
      console.log(result);
  }

  return (
    <div>
      <input type ="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="名前を入力"></input>
      <button onClick={create}>
        登録
      </button>

      <button onClick={readAll}>全件取得</button>
      {lists.map((list)=>(
        <div key={list.id}>
          {list.id}/{list.name}

          <button onClick={() => handledelete(list.id)}>
            削除
          </button>

          <input type ="text" value={updatename} onChange={(e) => updatesetName(e.target.value)} placeholder="変更後の名前を入力"></input>
          <button onClick={() => update(list.id)}>
            更新
          </button>
        </div>
      ))}
    </div>
  );*/
  const [loginUser, setLoginUser] = useState<LoginUser | null>(null);
  console.log(loginUser);
  const [pages, setPages] = useState<"users" | "incidents" | "loginform" | "incidentsRepo">("loginform");
  const [departments, setDepartments] = useState<Department[]>([]);
  const [roles, setRoles] = useState<Role[]>([]);

  useEffect(() => {
    fetch("http://localhost:8080/msts/departments", { credentials: "include" })
    .then(res => res.json())
    .then(data => {setDepartments(data);});

    fetch("http://localhost:8080/msts/roles", { credentials: "include" })
    .then(res => res.json())
    .then(data => {setRoles(data);});
  }, []);

  const [reloadkey, setReloadkey] = useState(0);
  const [updateUser, setUpdateUser] = useState<User | null>(null);
  return(
    <>

    <button onClick={() => setPages("users")}>ユーザー管理画面</button>
    <button onClick={() => setPages("incidents")}>インシデント管理</button>
    <button onClick={() => setPages("loginform")}>ログイン画面</button>
    <button onClick={() => setPages("incidentsRepo")}>異常対応データ統計</button>
    
    {pages === "users" && (
    <>
      <UserCreateForm onCreated={() => setReloadkey(prev => prev + 1)}
        departments={departments}
        roles={roles}/>

      <UserUpdateForm user={updateUser} onCreated={() => setReloadkey(prev => prev + 1)}
        departments={departments}
        roles={roles}/>
      <UserList reloadkey={reloadkey} onEdit={setUpdateUser} />
    </>
    )}

    {pages === "incidents" &&(
    <>
      <IncidentReportForm />    
    </>
    )}  

    {pages === "loginform" &&(
    <>
      <LoginForm setLoginUser={setLoginUser}/>    
    </>
    )}  

        {pages === "incidentsRepo" &&(
    <>
      <IncidentReportList />  
    </>
    )}  
    </>
  );
}

export default App;