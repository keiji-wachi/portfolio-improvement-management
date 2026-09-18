import { useEffect, useState } from "react";

import { getIncidents } from "../../api/incident/incidentApi";
import type { IncidentReport } from "../../types/incident";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";

import {
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  LineChart,
  Line,
} from "recharts";

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

  const formatDate = (reportedAt: string) => {
    return reportedAt.split("T")[0].replaceAll("-", "/");
  };

  const totalCount = reports.length;

  const processCount = new Set(
    reports.map((report) => report.processName)
  ).size;

  const incidentTypeCount = new Set(
    reports.map((report) => report.incidentTypeName)
  ).size;

  const incidentTypeChartData = Object.entries(
  reports.reduce<Record<string, number>>(
    (acc, report) => {
      const name = report.incidentTypeName;

      acc[name] = (acc[name] ?? 0) + 1;

      return acc;
    },
    {}
  )
).map(([name, value]) => ({
  name,
  value,
}));

const processChartData = Object.entries(
  reports.reduce<Record<string, number>>(
    (acc, report) => {
      const name = report.processName;

      acc[name] = (acc[name] ?? 0) + 1;

      return acc;
    },
    {}
  )
).map(([name, value]) => ({
  name,
  value,
}));

const dailyChartData = Object.entries(
  reports.reduce<Record<string, number>>(
    (acc, report) => {
      const date = report.reportedAt.slice(0, 10);

      acc[date] = (acc[date] ?? 0) + 1;

      return acc;
    },
    {}
  )
)
  .map(([date, value]) => ({
    date,
    value,
  }))
  .sort((a, b) =>
    a.date.localeCompare(b.date)
  );

const PIE_COLORS = [
  "#ef4444",
  "#f59e0b",
  "#3b82f6",
  "#10b981",
  "#8b5cf6",
];

return (
  <div className="statistics-content">

    {/* 検索条件 */}
    <div className="statistics-filter-card">

      <div className="statistics-card-header">
        <h2>集計条件</h2>
      </div>

      <div className="statistics-filter-body">

        <div className="statistics-filter-group">
          <label>対象月</label>

          <input
            type="month"
            value={targetMonth}
            onChange={(e) =>
              setTargetMonth(e.target.value)
            }
          />
        </div>

        <button
          className="btn-primary"
          onClick={fetchReports}
          disabled={isLoading}
        >
          {isLoading ? "検索中..." : "検索"}
        </button>

      </div>

    </div>


    {/* KPI */}
    <div className="statistics-summary">

      <div className="statistics-summary-card">
        <span className="statistics-summary-label">
          総異常件数
        </span>

        <strong className="statistics-summary-value">
          {totalCount}
        </strong>

        <span className="statistics-summary-unit">
          件
        </span>
      </div>


      <div className="statistics-summary-card">
        <span className="statistics-summary-label">
          発生工程数
        </span>

        <strong className="statistics-summary-value">
          {processCount}
        </strong>

        <span className="statistics-summary-unit">
          工程
        </span>
      </div>


      <div className="statistics-summary-card">
        <span className="statistics-summary-label">
          異常種別数
        </span>

        <strong className="statistics-summary-value">
          {incidentTypeCount}
        </strong>

        <span className="statistics-summary-unit">
          種類
        </span>
      </div>

    </div>


    {/* グラフ */}
    <div className="statistics-chart-grid">

      {/* 異常種別別 */}
      <div className="statistics-chart-card">

        <div className="statistics-card-header">
          <h2>異常種別別の発生件数</h2>
        </div>

        <div className="statistics-chart-body">

          {incidentTypeChartData.length > 0 ? (
            <div className="statistics-chart">

              <ResponsiveContainer
                width="100%"
                height="100%"
              >
                <PieChart>

                  <Pie
                    data={incidentTypeChartData}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="45%"
                    outerRadius={100}
                  >
                    {incidentTypeChartData.map(
                      (entry, index) => (
                        <Cell
                          key={entry.name}
                          fill={
                            PIE_COLORS[
                              index % PIE_COLORS.length
                            ]
                          }
                        />
                      )
                    )}
                  </Pie>

                  <Tooltip />
                  <Legend />

                </PieChart>
              </ResponsiveContainer>

            </div>
          ) : (
            <p className="statistics-empty">
              データがありません
            </p>
          )}

        </div>

      </div>


      {/* 工程別 */}
      <div className="statistics-chart-card">

        <div className="statistics-card-header">
          <h2>工程別の発生件数</h2>
        </div>

        <div className="statistics-chart-body">

          {processChartData.length > 0 ? (
            <div className="statistics-chart">

              <ResponsiveContainer
                width="100%"
                height="100%"
              >
                <BarChart data={processChartData}>

                  <CartesianGrid
                    strokeDasharray="3 3"
                  />

                  <XAxis
                    dataKey="name"
                    tick={{ fontSize: 12 }}
                  />

                  <YAxis
                    allowDecimals={false}
                  />

                  <Tooltip />

                  <Bar
                    dataKey="value"
                    name="発生件数"
                    fill="#3b82f6"
                    radius={[6, 6, 0, 0]}
                  />

                </BarChart>
              </ResponsiveContainer>

            </div>
          ) : (
            <p className="statistics-empty">
              データがありません
            </p>
          )}

        </div>

      </div>


      {/* 日別推移 */}
      <div className="statistics-chart-card statistics-chart-wide">

        <div className="statistics-card-header">
          <h2>日別の異常発生件数推移</h2>
        </div>

        <div className="statistics-chart-body">

          {dailyChartData.length > 0 ? (
            <div className="statistics-chart">

              <ResponsiveContainer
                width="100%"
                height="100%"
              >
                <LineChart data={dailyChartData}>

                  <CartesianGrid
                    strokeDasharray="3 3"
                  />

                  <XAxis
                    dataKey="date"
                    tick={{ fontSize: 12 }}
                  />

                  <YAxis
                    allowDecimals={false}
                  />

                  <Tooltip />

                  <Line
                    type="monotone"
                    dataKey="value"
                    name="異常件数"
                    stroke="#1c31eb"
                    strokeWidth={2}
                  />

                </LineChart>
              </ResponsiveContainer>

            </div>
          ) : (
            <p className="statistics-empty">
              データがありません
            </p>
          )}

        </div>

      </div>

    </div>


    {/* 一覧 */}
    <div className="statistics-table-card">

      <div className="statistics-table-header">

        <h2>
          異常対応データ一覧
        </h2>

        <span>
          {totalCount}件
        </span>

      </div>


      <div className="statistics-table-wrapper">

        <table className="statistics-table">

          <thead>
            <tr>
              <th>日付</th>
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

                <td>
                  {formatDate(report.reportedAt)}
                </td>

                <td>
                  {report.reportUserName}
                </td>

                <td>
                  {report.processName}
                </td>

                <td>
                  {report.incidentTypeName}
                </td>

                <td>
                  {report.incidentDetail}
                </td>

                <td>
                  {report.actionTaken || "－"}
                </td>

              </tr>
            ))}

          </tbody>

        </table>

      </div>

    </div>

  </div>
);
}

export default IncidentReportList;