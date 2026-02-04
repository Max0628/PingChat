// 定義 API 返回的用戶資料型別
export interface UserDTO {
  id: string;
  name: string;
  email: string;
  token: string;
}

import axios from "axios";

// Google 登入 API，回傳 UserDTO 型別
export const loginWithGoogle = async (): Promise<UserDTO> => {
  const response = await axios.post<UserDTO>("/api/auth/google");
  return response.data;
};