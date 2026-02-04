import { Routes, Route, Link } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import ChatPage from "./pages/ChatPage";

function App() {
  return (
    <div className="app-container p-4">
      <nav className="mb-4">
        <Link to="/login" className="mr-4 text-blue-600">Login</Link>
        <Link to="/chat" className="text-blue-600">Chat</Link>
      </nav>

      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/chat" element={<ChatPage />} />
      </Routes>
    </div>
  );
}

export default App;
