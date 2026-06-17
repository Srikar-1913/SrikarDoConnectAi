import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080"
});

export const getAIAnswer = (data) => {
  return API.post("http://localhost:8081/ai/generate", data);
};


API.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");

  if (token) {
    config.headers = {
      ...config.headers,
      Authorization: `Bearer ${token}`
    };
  }

  return config;
});

export default API;