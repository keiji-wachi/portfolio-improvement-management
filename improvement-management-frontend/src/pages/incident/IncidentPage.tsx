import IncidentReportForm from "../../components/incident/IncidentReportForm";

import { TriangleAlert } from "lucide-react";

function IncidentPage() {
  return (
<div className="page-container">

  <div className="page-header">
    <div className="page-header-icon">
      <TriangleAlert/>
    </div>

    <div>
      <h1>異常対応入力</h1>
      <p>工場・設備の異常事象を記録します</p>
    </div>
  </div>

  <IncidentReportForm />

</div>
  );
}

export default IncidentPage;