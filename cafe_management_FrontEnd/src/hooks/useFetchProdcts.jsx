import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "@/state/products/productsSlice";

const useFetchProducts = (search) => {
  const dispatch = useDispatch();
  const { products, error, orders, status } = useSelector((state) => state.products);

  useEffect(() => {
   
   
      dispatch(fetchProducts( search ));
  
  }, [dispatch, search]); // Only re-fetch when search or fetchType changes


      return { products, orders, error, status };

};

export default useFetchProducts;