import { useState } from 'react';

// Google OAuth settings
const CLIENT_ID ='324950861536-edd2ktjs9fv5khukro5b0ukelovnt68l.apps.googleusercontent.com';
const REDIRECT_URI = 'http://localhost:5173/chat';
const SCOPE = 'openid profile email';

type AuthMode = 'login' | 'register';

export default function LoginPage() {
  const [isLoading, setIsLoading] = useState(false);
  const [authMode, setAuthMode] = useState<AuthMode>('login');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState(''); // 新增 username 狀態
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleGoogleLogin = () => {
    setIsLoading(true);
    const state = Math.random().toString(36).substring(2); // prevent CSRF
    const oauthUrl =
      `https://accounts.google.com/o/oauth2/v2/auth?` +
      `client_id=${CLIENT_ID}` +
      `&redirect_uri=${encodeURIComponent(REDIRECT_URI)}` +
      `&response_type=token` +
      `&scope=${encodeURIComponent(SCOPE)}` +
      `&state=${state}`;
    window.location.href = oauthUrl;
  };

  // 虛擬 API 請求，請替換為實際 API
  const fakeApi = async (mode: AuthMode, email: string, password: string, username?: string) => {
    await new Promise((r) => setTimeout(r, 800));
    if (!email || !password || (mode === 'register' && !username)) return { error: '請填寫所有欄位' };
    if (mode === 'register') {
      if (email === 'test@pingchat.com') return { error: 'Email 已被註冊' };
      return { success: `註冊成功，請登入 (${username})` };
    }
    if (mode === 'login') {
      if (email !== 'test@pingchat.com' || password !== '123456') return { error: '帳號或密碼錯誤' };
      return { success: '登入成功' };
    }
    return { error: '未知錯誤' };
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setSuccess('');
    setIsLoading(true);
    const res = await fakeApi(authMode, email, password, username);
    setIsLoading(false);
    if (res.error) setError(res.error);
    if (res.success) setSuccess(res.success);
  };

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gradient-to-br from-blue-500 to-purple-600 text-white">
      <div className="bg-white text-gray-800 p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-4xl font-extrabold mb-6 text-center">
          Welcome to PingChat
        </h1>
        <p className="text-center mb-4">
          Login to start chatting with your friends!
        </p>
        <div className="mb-6">
          <button
            onClick={handleGoogleLogin}
            className={`w-full px-6 py-3 text-lg font-semibold bg-blue-500 rounded-lg hover:bg-blue-600 transition-all duration-300 mb-2 ${
              isLoading ? 'opacity-50 cursor-not-allowed' : ''
            }`}
            disabled={isLoading}
          >
            {isLoading ? (
              <div className="flex items-center justify-center">
                <svg
                  className="animate-spin h-5 w-5 mr-3 text-white"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                >
                  <circle
                    className="opacity-25"
                    cx="12"
                    cy="12"
                    r="10"
                    stroke="currentColor"
                    strokeWidth="4"
                  ></circle>
                  <path
                    className="opacity-75"
                    fill="currentColor"
                    d="M4 12a8 8 0 018-8v4a4 4 0 00-4 4H4z"
                  ></path>
                </svg>
                Logging in...
              </div>
            ) : (
              'Login with Google'
            )}
          </button>
          <div className="text-center text-xs text-gray-500">or</div>
        </div>
        <form onSubmit={handleSubmit} className="flex flex-col gap-3">
          {authMode === 'register' && (
            <input
              type="text"
              placeholder="Username"
              className="px-4 py-2 rounded border border-gray-300 focus:outline-none focus:ring-2 focus:ring-purple-400"
              value={username}
              onChange={e => setUsername(e.target.value)}
              disabled={isLoading}
              autoComplete="username"
              required
            />
          )}
          <input
            type="email"
            placeholder="Email"
            className="px-4 py-2 rounded border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-400"
            value={email}
            onChange={e => setEmail(e.target.value)}
            disabled={isLoading}
            autoComplete="username"
            required
          />
          <input
            type="password"
            placeholder="Password"
            className="px-4 py-2 rounded border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-400"
            value={password}
            onChange={e => setPassword(e.target.value)}
            disabled={isLoading}
            autoComplete={authMode === 'login' ? 'current-password' : 'new-password'}
            required
          />
          <button
            type="submit"
            className={`w-full px-6 py-3 text-lg font-semibold rounded-lg transition-all duration-300 ${
              authMode === 'login'
                ? 'bg-green-500 hover:bg-green-600'
                : 'bg-purple-500 hover:bg-purple-600'
            } ${isLoading ? 'opacity-50 cursor-not-allowed' : ''}`}
            disabled={isLoading}
          >
            {authMode === 'login' ? 'Login' : 'Register'}
          </button>
        </form>
        <div className="flex justify-between mt-3 text-sm">
          <button
            type="button"
            className={`underline ${authMode === 'login' ? 'text-blue-600' : 'text-gray-400'}`}
            onClick={() => { setAuthMode('login'); setError(''); setSuccess(''); setUsername(''); }}
            disabled={authMode === 'login'}
          >
            Login
          </button>
          <button
            type="button"
            className={`underline ${authMode === 'register' ? 'text-purple-600' : 'text-gray-400'}`}
            onClick={() => { setAuthMode('register'); setError(''); setSuccess(''); }}
            disabled={authMode === 'register'}
          >
            Register
          </button>
        </div>
        {error && <div className="mt-3 text-red-500 text-center text-sm">{error}</div>}
        {success && <div className="mt-3 text-green-500 text-center text-sm">{success}</div>}
      </div>
    </div>
  );
}
