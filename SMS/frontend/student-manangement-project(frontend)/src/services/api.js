import { getToken } from "./authService";

export const authFetch = async (url, options = {}) => {
  const token = getToken();

  const defaultHeaders = {
    "Content-Type": "application/json",
    "Authorization": `Bearer ${token}`,
  };

  const finalOptions = {
    ...options,
    headers: {
      ...defaultHeaders,
      ...(options.headers || {}),
    },
  };

  const response = await fetch(url, finalOptions);
  return response;
};