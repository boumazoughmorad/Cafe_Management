import {
  createContext,
  useContext,
  useEffect,
  useState,
} from 'react';
import { 
  getItem,
  setItem, 
  removeItem 
} from '@/lib/utils/localStorage'; 


import api from '@/api';


const AuthContext = createContext(undefined);


export const useAuth = () => {
  const authContext = useContext(AuthContext);

  if (!authContext) {
    throw new Error('useAuth must be used within an AuthProvider');
  }

  return authContext;
};

const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(getItem('token') || null);
  const [user, setUser] = useState(getItem('user') || null);



  const validateToken = async () => {
    if (!token) {
      console.log('Token does not exist');
      clearAuthData();
      
      return false;
    }

    try {
     
      const dataToken = {
        token : token
      }
     
      const response = await api.get('/api/checkToken',dataToken);
      console.log('Response:', response.data);
  

      if (!response.data?.checkToken) {
        console.log('Token is invalid or expired');
        clearAuthData();
    
        return false;
      }
    } catch (error) {
      console.log('Error validating token:', error);
      clearAuthData();
  
      return false;
    }
    return true;
  };

 
  const clearAuthData = () => {
    setToken(null);
    setUser(null);
    removeItem('token'); 
    removeItem('user');
  };

  const handleTokenValidation = async () => {
    const isValid = await validateToken();
    if (isValid) {
      setItem('token', token);
      setItem('user', user);
      return true;
    }
    else {
      clearAuthData();
      return false;
    }
  };






  return (
    <AuthContext.Provider value={{ token, setToken, user, setUser, clearAuthData,validateToken, handleTokenValidation }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;