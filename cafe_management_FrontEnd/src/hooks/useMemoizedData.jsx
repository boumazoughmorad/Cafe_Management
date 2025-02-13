import { useMemo } from 'react';
import { getImageUrl } from '@/lib/utils/images';
const findProductByID=(id,products)=>{
    return (products||[]).find((product)=>{
      return product.id===id
    })    
  }


// Custom hook
const useMemoizedData = (data, isOrderData,products=[]) => {
  return useMemo(() => {
    if (isOrderData) {
      // Transform orders data
      return (data || []).map((order) => {  
         
        const product = findProductByID(order?.prouductId,products);

        
        return {
          ...order,
          id: product?.id,
          name: product?.name,
          imageUrl: getImageUrl(product?.image),
          type: product?.type,
          description: product?.description,
          price: product?.price,
          isExist: product?.isExist,
        };
      });
    } else {
      // Transform products data
      return (data || []).map((product) => ({
        ...product,
        imageUrl: getImageUrl(product.image),
      }));
    }
  }, [data, isOrderData]);
};

export default useMemoizedData;