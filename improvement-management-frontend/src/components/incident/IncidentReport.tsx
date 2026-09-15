import { useEffect, useState } from "react";

import { getIncidents } from "../../api/incident/incidentApi";
import type { IncidentReport } from "../../types/incident";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

function IncidentReportList() {
  const handleApiError = useApiErrorHandler();
  const [reports, setReports] = useState<IncidentReport[]>([]);
  const [targetMonth, setTargetMonth] = useState("2026-08");

  const [isLoading, setIsLoading] = useState(false);

  const fetchReports = async () => {
  setIsLoading(true);

  try {
    const data = await getIncidents(targetMonth);
    setReports(data);
  } catch (error) {
    handleApiError(error);
  } finally {
    setIsLoading(false);
  }
};

  useEffect(() => {
    fetchReports();
  }, []);

  return (
    <div>
      <h2>異常対応データ統計</h2>

      <input
        type="month"
        value={targetMonth}
        onChange={(e) => setTargetMonth(e.target.value)}
      />

      <button onClick={fetchReports} disabled={isLoading}>
        {isLoading ? "検索中..." : "検索"}
      </button>

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
          {reports.map((report) => (
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
  );
}

export default IncidentReportList;