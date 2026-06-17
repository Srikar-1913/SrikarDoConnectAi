import axios from "axios";

// axios instance
const API = axios.create({
  baseURL: "http://localhost:8080"
});

// interceptor to attach token
API.interceptors.request.use((config) => {

  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

// AI service call (separate URL)
export const getAIAnswer = (data) => {
  return axios.post("http://localhost:8081/ai/generate", data);
};

export default API;