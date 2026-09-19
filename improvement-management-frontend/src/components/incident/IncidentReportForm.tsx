import { useEffect, useState } from "react";

import { createIncident } from "../../api/incident/incidentApi";
import { getProcesses, getIncidentTypes } from "../../api/master/masterApi";

import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { useToast } from "../../hooks/common/useToast";

import { ApiError } from "../../api/client";

import type { Process, IncidentType } from "../../types/master";

import ErrorMessage from "../common/ErrorMessage";
import ConfirmDialog from "../common/ConfirmDialog";

function IncidentReportForm() {

  const [processes, setProcesses] = useState<Process[]>([]);
  const [incidentTypes, setIncidentTypes] = useState<IncidentType[]>([]);

  const [occurredProcessId, setOccurredProcessId] = useState(0);
  const [incidentTypeId, setIncidentTypeId] = useState(0);
  const [incidentDetail, setIncidentDetail] = useState("");
  const [actionTaken, setActionTaken] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const [errors, setErrors] = useState<{
  occurredProcessId?: string;
  incidentTypeId?: string;
  incidentDetail?: string;
  }>({});

  const validate = () => {
  const newErrors: {
    occurredProcessId?: string;
    incidentTypeId?: string;
    incidentDetail?: string;
  } = {};

  if (occurredProcessId === 0) {
    newErrors.occurredProcessId =
      "対象工程を選択してください";
  }

  if (incidentTypeId === 0) {
    newErrors.incidentTypeId =
      "異常タイプを選択してください";
  }

  if (!incidentDetail.trim()) {
    newErrors.incidentDetail =
      "異常原因を入力してください";
  }

  setErrors(newErrors);

  return Object.keys(newErrors).length === 0;
  };

  const handleConfirm = (  e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    setIsConfirmOpen(true);
  };

  const handleApiError = useApiErrorHandler();
  const { showToast } = useToast();

  useEffect(() => {

    const fetchMasters = async () => {

      try {

        const [
          processData,
          incidentTypeData,
        ] = await Promise.all([
          getProcesses(),
          getIncidentTypes(),
        ]);

        setProcesses(processData);
        setIncidentTypes(incidentTypeData);

      } catch (error) {
        handleApiError(error);
      }
    };

    fetchMasters();

  }, []);

  const selectedProcess = processes.find(
    (process) =>
      process.processId === occurredProcessId
  );

  const selectedIncidentType = incidentTypes.find(
    (type) =>
      type.incidentTypeId === incidentTypeId
  );

  const incidentCreate = async () => {

    setErrorMessage("");
    setIsLoading(true);

    try {

      await createIncident({
        occurredProcessId,
        incidentTypeId,
        incidentDetail,
        actionTaken,
      });

      setIsConfirmOpen(false);

      showToast("異常対応を登録しました");

      setOccurredProcessId(0);
      setIncidentTypeId(0);
      setIncidentDetail("");
      setActionTaken("");

    } catch (error) {

      if (
        error instanceof ApiError &&
        error.status === 400
      ) {
        setErrorMessage(error.message);
        setIsConfirmOpen(false);
        return;
      }

      handleApiError(error);

    } finally {
      setIsLoading(false);
    }
  };

  return (
  <>
    <div className="form-card">

      <div className="form-card-header">
        <h2>異常情報</h2>
      </div>

      <div className="form-card-body">

        <ErrorMessage message={errorMessage} />

        <form onSubmit={handleConfirm}>

          <div className="form-group">
            <label>
              対象工程
              <span className="required-mark">*</span>
            </label>

            <select
              value={occurredProcessId}
              onChange={(e) =>
                setOccurredProcessId(Number(e.target.value))
              }
            >
              <option value={0}>
                工程を選択してください
              </option>

              {processes.map((process) => (
                <option
                  key={process.processId}
                  value={process.processId}
                >
                  {process.processName}
                </option>
              ))}
            </select>

            {errors.occurredProcessId && (
              <p className="field-error">
                {errors.occurredProcessId}
              </p>
            )}
          </div>

          <div className="form-group">
            <label>
              異常タイプ
              <span className="required-mark">*</span>
            </label>

            <select
              value={incidentTypeId}
              onChange={(e) =>
                setIncidentTypeId(Number(e.target.value))
              }
            >
              <option value={0}>
                異常タイプを選択してください
              </option>

              {incidentTypes.map((type) => (
                <option
                  key={type.incidentTypeId}
                  value={type.incidentTypeId}
                >
                  {type.incidentTypeName}
                </option>
              ))}
            </select>

            {errors.incidentTypeId && (
              <p className="field-error">
                {errors.incidentTypeId}
              </p>
            )}
          </div>

          <div className="form-group">
            <label>
              異常原因
              <span className="required-mark">*</span>
            </label>

            <textarea
              value={incidentDetail}
              onChange={(e) =>
                setIncidentDetail(e.target.value)
              }
              placeholder="異常の状況を詳しく入力してください"
              rows={5}
            />

            {errors.incidentDetail && (
              <p className="field-error">
                {errors.incidentDetail}
              </p>
            )}
          </div>

          <div className="form-group">
            <label>処置内容</label>

            <textarea
              value={actionTaken}
              onChange={(e) =>
                setActionTaken(e.target.value)
              }
              placeholder="実施した処置内容を入力してください"
              rows={4}
            />
          </div>

          <div className="form-actions">
            <button
              className="btn-primary"
              type="submit"
              disabled={isLoading}
            >
              登録内容を確認
            </button>
          </div>

        </form>

      </div>
    </div>

    <ConfirmDialog
      open={isConfirmOpen}
      title="異常対応登録確認"
      message="以下の内容で異常対応を登録しますか？"
      confirmText="登録する"
      cancelText="キャンセル"
      isLoading={isLoading}
      onConfirm={incidentCreate}
      onCancel={() => setIsConfirmOpen(false)}
    >
      <div className="confirm-detail-list">

        <div className="confirm-detail-row">
          <span className="confirm-detail-label">
            対象工程
          </span>

          <span className="confirm-detail-value">
            {selectedProcess?.processName}
          </span>
        </div>

        <div className="confirm-detail-row">
          <span className="confirm-detail-label">
            異常タイプ
          </span>

          <span className="confirm-detail-value">
            {selectedIncidentType?.incidentTypeName}
          </span>
        </div>

        <div className="confirm-detail-row">
          <span className="confirm-detail-label">
            異常原因
          </span>

          <span className="confirm-detail-value">
            {incidentDetail}
          </span>
        </div>

        <div className="confirm-detail-row">
          <span className="confirm-detail-label">
            処置内容
          </span>

          <span className="confirm-detail-value">
            {actionTaken || "なし"}
          </span>
        </div>

      </div>
    </ConfirmDialog>
  </>
);
}

export default IncidentReportForm;