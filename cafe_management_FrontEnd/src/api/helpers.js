// import * as jose from 'jose';

// import { env } from '@/lib/env';



import axios from "axios";
import { getItem } from "@/lib/utils/localStorage";

// Wrapper for Axios that adds authentication checks
export const withAuth =
  (...data) =>
  async (config) => {
    const token = getItem("token"); 

    if (!token) {
      console.warn("Unauthorized request: No token found");
      throw new Error("Unauthorized");
    }

    config.headers = {
      ...config.headers,
      Authorization: `Bearer ${token}`,
    };

    try {
      return await axios(config, ...data);
    } catch (error) {
      console.error("Request failed:", error);
      if (error.response && error.response.status === 401) {
        console.warn("Token expired or invalid. Redirecting to login...");
        localStorage.removeItem("token");
        window.location.href = "/login"; 
      }
      throw error;
    }
  };
