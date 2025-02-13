import { env } from '@/lib/env';
import axios from 'axios';


const url_user = env.BASE_URL+"user/"

export const signin = async (data) => {
  const { email, password } = data;
    
    
  try {
    const response = await axios.post(url_user+"login", {
        email,
        password,
    });
    return response.data;
} catch (error) {
    console.log("error :",error);
    
    return [401, { error: error }];
}


};


export const checkToken = async (token) => {
    try {
   
        
        
      const response = await axios.get(url_user+"checkToken",{
        headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
          withCredentials: true,
      });
    
      return response.data;
  } catch (error) {
      return [401, { error: error }];
  }
}