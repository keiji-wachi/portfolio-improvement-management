import IncidentReportList from "../../components/incident/IncidentReport";

import { ChartNoAxesCombined } from "lucide-react";

import "../../styles/Incident/IncidentReportList.css"
function StatisticsPage() {
return (
  <main>
    <div className="page-container-wide">

      <div className="page-header">
        <div className="page-header-icon">
          <ChartNoAxesCombined/>
        </div>

        <div>
          <h1>異常対応データ統計</h1>
          <p>部署内の異常傾向を可視化します</p>
        </div>
      </div>

      <IncidentReportList />

    </div>
  </main>
);
}

export default StatisticsPage;