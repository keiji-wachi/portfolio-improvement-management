import { useState } from "react";

import { createIncident } from "../../api/incident/incidentApi";
import { useApiErrorHandler } from "../../hooks/api/useApiErrorHandler";
import { ApiError } from "../../api/client";

import ErrorMessage from "../common/ErrorMessage";
import ConfirmDialog from "../common/ConfirmDialog";
import { useToast } from "../../hooks/common/useToast";

function IncidentReportForm() {
  const [occurredProcessId, setOccurredProcessId] = useState(0);
  const [incidentTypeId, setIncidentTypeId] = useState(0);
  const [incidentDetail, setIncidentDetail] = useState("");
  const [actionTaken, setActionTaken] = useState("");

  const [isLoading, setIsLoading] = useState(false);
  const [isConfirmOpen, setIsConfirmOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleApiError = useApiErrorHandler();
  const { showToast } = useToast();

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
      if (error instanceof ApiError && error.status === 400) {
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
    <div>
      <h2>異常対応入力</h2>

      <ErrorMessage message={errorMessage} />

      <div>
        <label>対象工程</label>
        <input
          type="number"
          value={occurredProcessId}
          onChange={(e) =>
            setOccurredProcessId(Number(e.target.value))
          }
        />
      </div>

      <div>
        <label>異常タイプ</label>
        <input
          type="number"
          value={incidentTypeId}
          onChange={(e) =>
            setIncidentTypeId(Number(e.target.value))
          }
        />
      </div>

      <div>
        <label>異常原因</label>
        <input
          type="text"
          value={incidentDetail}
          onChange={(e) => setIncidentDetail(e.target.value)}
        />
      </div>

      <div>
        <label>処置内容</label>
        <input
          type="text"
          value={actionTaken}
          onChange={(e) => setActionTaken(e.target.value)}
        />
      </div>

      <button
        onClick={() => setIsConfirmOpen(true)}
        disabled={isLoading}
      >
        登録
      </button>

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
        <div>
          <p>対象工程ID：{occurredProcessId}</p>
          <p>異常タイプID：{incidentTypeId}</p>
          <p>異常原因：{incidentDetail}</p>
          <p>処置内容：{actionTaken}</p>
        </div>
      </ConfirmDialog>
    </div>
  );
}

export default IncidentReportForm;