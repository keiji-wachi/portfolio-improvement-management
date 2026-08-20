import { useState } from "react";

function IncidentReportForm(){
    const [departmentId, setDepartmentId] = useState(0);
    const [report_user_id, setReportUserId] = useState(0);
    const [occurred_process_id, setOccurredProcessId] = useState(0);
    const [incident_type_id, setIncidentTypeId] = useState(0);
    const [incident_detail, setIncidentDetail] = useState("");
    const [action_taken, setActionTaken] = useState("");

    const incidentCreate = async () => {
    const response = await fetch(`http://localhost:8080/incident`,{
        method:"POST",

        headers: {
            "Content-Type": "application/json",
        },

        body: JSON.stringify({
            department_id: departmentId,
            report_user_id: report_user_id,
            occurred_process_id: occurred_process_id,
            incident_type_id: incident_type_id,
            incident_detail: incident_detail,
            action_taken: action_taken
        }),
      });

    const result = await response.text();
    console.log(result);
    }

    return (
        <div>
            <h2>異常対応入力</h2>

            <div>
                <label>部署</label>
                <input type="number" value={departmentId} onChange={(e) => setDepartmentId(Number(e.target.value))} />
            </div>            

            <div>
                <label>作成者</label>
                <input type="number" value={report_user_id} onChange={(e) => setReportUserId(Number(e.target.value))} />  
            </div>

            <div>
                <label>対象工程</label>
                <input type="number" value={occurred_process_id} onChange={(e) => setOccurredProcessId(Number(e.target.value))} />  
            </div>

            <div>
                <label>異常タイプ</label>
                <input type="number" value={incident_type_id} onChange={(e) => setIncidentTypeId(Number(e.target.value))} />  
            </div>

            <div>
                <label>異常原因</label>
                <input type="text" value={incident_detail} onChange={(e) => setIncidentDetail(e.target.value)}/>
            </div>

            <div>
                <label>処置内容</label>
                <input type="text" value={action_taken} onChange={(e) => setActionTaken(e.target.value)}/>
            </div>

            <button onClick={incidentCreate}>登録</button>
        </div>
    );
}

export default IncidentReportForm;