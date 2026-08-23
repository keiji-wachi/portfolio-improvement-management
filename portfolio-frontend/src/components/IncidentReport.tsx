import { useEffect, useState } from "react";

type incidentReport = {
    incidentId: number;
    departmentName: string;
    reportUserName: string;
    reportedAt: string;
    processName: string;
    incidentTypeName: string;
    incidentDetail: string;
    actionTaken: string;
}

function IncidentReportList(){
    const [report, setReports] = useState<incidentReport[]>([]);
    const [targetMonth, setTargetMonth] = useState("2026-08");

    const fetchReports = async () => {
        const response = await fetch(`http://localhost:8080/incident?targetMonth=${targetMonth}`, { credentials: "include" });

        const data = await response.json();
        console.log(data)
        setReports(data);
    };

    useEffect(() => {
        fetchReports();
    },[]);

    return (
        <div>
            <h2>異常対応データ統計</h2>

            <input type="month" value={targetMonth} onChange={(e) => setTargetMonth(e.target.value)}/>

            <button onClick={fetchReports}>検索</button>

            <table>
                <thead>
                    <tr>
                        <th>日時</th>
                        <th>部署</th>
                        <th>報告者</th>
                        <th>工程</th>
                        <th>異常区分</th>
                        <th>詳細</th>
                        <th>対応内容</th>
                    </tr>
                </thead>

                <tbody>
                    {report.map((report) => (
                        <tr key={report.incidentId}>
                            <td>{report.reportedAt}</td>
                            <td>{report.departmentName}</td>
                            <td>{report.reportUserName}</td>
                            <td>{report.processName}</td>
                            <td>{report.incidentTypeName}</td>
                            <td>{report.incidentDetail}</td>
                            <td>{report.actionTaken}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
        
}

export default IncidentReportList;