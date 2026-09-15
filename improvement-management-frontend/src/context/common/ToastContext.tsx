import {createContext,useState, type ReactNode} from "react";

import Toast from "../../components/common/Toast";

type ToastContextType = {
  showToast: (message: string) => void;
};

export const ToastContext = createContext<ToastContextType | undefined>(
  undefined
);

type Props = {
  children: ReactNode;
};

export function ToastProvider({ children }: Props) {
  const [message, setMessage] = useState("");

  const showToast = (message: string) => {
    setMessage(message);

    setTimeout(() => {
      setMessage("");
    }, 3000);
  };

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}

      <Toast message={message} />
    </ToastContext.Provider>
  );
}