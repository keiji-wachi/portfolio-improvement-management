import "../../styles/common/Toast.css";

type Props = {
  message: string;
};

function Toast({ message }: Props) {
  if (!message) {
    return null;
  }

  return (
    <div className="toast">
      {message}
    </div>
  );
}

export default Toast;