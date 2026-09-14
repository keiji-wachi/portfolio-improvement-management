import AppRouter from "./routes/AppRouter";
import { AuthProvider } from "./context/auth/AuthContext";
import { ToastProvider } from "./context/common/ToastContext";

function App() {
  return (
    <ToastProvider>
      <AuthProvider>
        <AppRouter />
      </AuthProvider>
    </ToastProvider>
  );
}

export default App;