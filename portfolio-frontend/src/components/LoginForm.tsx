import { useState } from "react";
import type { LoginUser } from "../types/master";

type Props = {
    setLoginUser: React.Dispatch<React.SetStateAction<LoginUser | null>>
};

function LoginForm({setLoginUser} :Props){
    const [loginid, setLoginId] = useState("");
    const [password, setPassWord] = useState("");

    const LoginAuth = async () => {
        const response = await fetch("http://localhost:8080/login",{
        method:"POST",
        credentials: "include",

        headers: {
            "Content-Type": "application/json",
        },

        body: JSON.stringify({
            id: loginid,
            password: password,
        }),
    });

        const data = await response.json();

        if(data.SUCCES == "FALSE"){
            console.log("ログインエラー");
            return;
        }
        setLoginUser(data);

    };

    return (
        <div>
            <h2>ログイン画面</h2>

            <div>
                <label>ログインID</label>
                <input value={loginid} onChange={(e) => setLoginId(e.target.value)}/>
            </div>

            <div>
                <label>パスワード</label>
                <input value={password} onChange={(e) => setPassWord(e.target.value)}/>
            </div>           

            <button onClick={LoginAuth}>ログイン</button>
        </div>
    );
}

export default LoginForm