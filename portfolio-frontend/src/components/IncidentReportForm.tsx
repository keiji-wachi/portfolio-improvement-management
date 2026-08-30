import { useState } from "react";

function IncidentReportForm(){
    const [occurredProcessId, setOccurredProcessId] = useState(0);
    const [incidentTypeId, setIncidentTypeId] = useState(0);
    const [incidentDetail, setIncidentDetail] = useState("");
    const [actionTaken, setActionTaken] = useState("");

    const incidentCreate = async () => {
    const response = await fetch(`http://localhost:8080/incident`,{
        method:"POST",
        credentials: "include",

        headers: {
            "Content-Type": "application/json",
        },

            body: JSON.stringify({
                occurred_process_id: occurredProcessId,
                incident_type_id: incidentTypeId,
                incident_detail: incidentDetail,
                action_taken: actionTaken
            }),
      });

    const result = await response.text();
    console.log(result);
    }

    return (
        <div>
            <h2>異常対応入力</h2>

            <div>
                <label>対象工程</label>
                <input type="number" value={occurredProcessId} onChange={(e) => setOccurredProcessId(Number(e.target.value))} />  
            </div>

            <div>
                <label>異常タイプ</label>
                <input type="number" value={incidentTypeId} onChange={(e) => setIncidentTypeId(Number(e.target.value))} />  
            </div>

            <div>
                <label>異常原因</label>
                <input type="text" value={incidentDetail} onChange={(e) => setIncidentDetail(e.target.value)}/>
            </div>

            <div>
                <label>処置内容</label>
                <input type="text" value={actionTaken} onChange={(e) => setActionTaken(e.target.value)}/>
            </div>

            <button onClick={incidentCreate}>登録</button>
        </div>
    );
}

export default IncidentReportForm;