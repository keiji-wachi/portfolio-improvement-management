import type { ReactNode } from "react";

type Props = {
  open: boolean;
  title: string;
  message?: string;
  children?: ReactNode;
  confirmText?: string;
  cancelText?: string;
  isLoading?: boolean;
  onConfirm: () => void;
  onCancel: () => void;
};

function ConfirmDialog({
  open,
  title,
  message,
  children,
  confirmText = "確認",
  cancelText = "キャンセル",
  isLoading = false,
  onConfirm,
  onCancel,
}: Props) {
  if (!open) {
    return null;
  }

  return (
    <div className="confirm-dialog-overlay">
      <div className="confirm-dialog">

        <div className="confirm-dialog-header">
          <h3>{title}</h3>

          {message && (
            <p>{message}</p>
          )}
        </div>

        <div className="confirm-dialog-body">
          {children}
        </div>

        <div className="confirm-dialog-actions">
          <button
            type="button"
            className="btn-secondary"
            onClick={onCancel}
            disabled={isLoading}
          >
            {cancelText}
          </button>

          <button
            type="button"
            className="btn-primary"
            onClick={onConfirm}
            disabled={isLoading}
          >
            {isLoading ? "処理中..." : confirmText}
          </button>
        </div>

      </div>
    </div>
  );
}

export default ConfirmDialog;