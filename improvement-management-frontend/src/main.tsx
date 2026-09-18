import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import "./styles/common/Button.css"
import "./styles/common/AppLayout.css"
import "./styles/common/ConfirmDialog.css"
import "./styles/common/Sidebar.css"
import "./styles/common/Toast.css"
import "./styles/common/CommonPage.css"


createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>,
)
