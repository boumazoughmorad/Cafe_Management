import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { useAuth } from '@/components/AuthPrivider/AuthProvider';
import { Spinner } from '@/components/ui';

const Route = ({ children, isProtected }) => {
  const navigate = useNavigate();
  const { token,user,validateToken, handleTokenValidation } = useAuth();

  useEffect(() => {
    if(!validateToken())
      navigate('/SignIn');
  }, []);

  useEffect(() => {
    if(!handleTokenValidation())
      navigate('/SignIn');
  }, [token, user]);


  useEffect(() => {
    if (isProtected && token === null) {
      navigate('/SignIn', { replace: true });
    }
    if (isProtected && token === null) {

    }
  }, [isProtected, navigate, token]);

  return token === undefined ? (
    <div className='absolute bottom-0 left-0 right-0 top-0 ml-[700px] flex items-center justify-center'>
      <Spinner />
    </div>
  ) : (
    children
  );
};

export default Route;