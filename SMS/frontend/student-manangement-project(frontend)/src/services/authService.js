// Save token to localStorage
export const setToken = (token) => {
  localStorage.setItem("token", token);
};

// Get token from localStorage
export const getToken = () => {
  return localStorage.getItem("token");
};

// Remove token (used for logout)
export const removeToken = () => {
  localStorage.removeItem("token");
};

// Check if the user is logged in
export const isLoggedIn = () => {
  return !!getToken();
};

// Optional: get user role if you're storing it
export const getUserRole = () => {
  return localStorage.getItem("role");
};

// Optional: store user role
export const setUserRole = (role) => {
  localStorage.setItem("role", role);
};