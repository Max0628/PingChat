import { useEffect, useState } from "react";

// 解析 hash 參數
function parseHashParams(hash: string) {
  const params = new URLSearchParams(hash.replace(/^#/, ""));
  return {
    accessToken: params.get("access_token"),
    tokenType: params.get("token_type"),
    expiresIn: params.get("expires_in"),
    scope: params.get("scope"),
    state: params.get("state"),
    authuser: params.get("authuser"),
    prompt: params.get("prompt"),
  };
}

export default function ChatPage() {
  const [tokenInfo, setTokenInfo] = useState<any>(null);

  useEffect(() => {
    const hash = window.location.hash;
    if (hash) {
      const info = parseHashParams(hash);
      setTokenInfo(info);
      if (info.accessToken) {
        localStorage.setItem("access_token", info.accessToken);
      }
    }
  }, []);

  if (!tokenInfo || !tokenInfo.accessToken) {
    return <div>未取得 Google access token，請重新登入。</div>;
  }

  return (
    <div className="flex flex-col items-center justify-center h-screen bg-gradient-to-br from-blue-500 to-purple-600 text-white">
      <div className="bg-white text-gray-800 p-8 rounded-lg shadow-lg w-96">
        <h1 className="text-3xl font-bold mb-4 text-center">Google 登入成功！</h1>
        <p className="mb-2">Access Token 已存入 localStorage。</p>
        <div className="break-all text-xs bg-gray-100 p-2 rounded mb-4">{tokenInfo.accessToken}</div>
        <p>Token Type：{tokenInfo.tokenType}</p>
        <p>Expires In：{tokenInfo.expiresIn}</p>
        <p>Scope：{tokenInfo.scope}</p>
        {/* 這裡可以加上更多 UI 或自動導向主頁 */}
      </div>
    </div>
  );
}
