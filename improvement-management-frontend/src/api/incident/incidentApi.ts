import { apiFetch } from "../client";
import type {
  IncidentCreateRequest,
  IncidentReport,
} from "../../types/incident";

export async function getIncidents(
  targetMonth: string
): Promise<IncidentReport[]> {
  const response = await apiFetch(
    `/incident?targetMonth=${encodeURIComponent(targetMonth)}`
  );

  return response.json();
}

export async function createIncident(
  body: IncidentCreateRequest
) {
  return apiFetch("/incident", {
    method: "POST",
    body: JSON.stringify({
      occurred_process_id: body.occurredProcessId,
      incident_type_id: body.incidentTypeId,
      incident_detail: body.incidentDetail,
      action_taken: body.actionTaken,
    }),
  });
}