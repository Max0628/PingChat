import { create } from "zustand";

interface AuthState {
  user: any; // Replace with a proper User type
  setUser: (user: any) => void;
}

export const useAuthStore = create<AuthState>((set) => ({
  user: null,
  setUser: (user) => set({ user }),
}));