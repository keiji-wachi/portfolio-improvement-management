export type IncidentReport = {
  incidentId: number;
  departmentName: string;
  reportUserName: string;
  reportedAt: string;
  processName: string;
  incidentTypeName: string;
  incidentDetail: string;
  actionTaken: string;
};

export type IncidentCreateRequest = {
  occurredProcessId: number;
  incidentTypeId: number;
  incidentDetail: string;
  actionTaken: string;
};