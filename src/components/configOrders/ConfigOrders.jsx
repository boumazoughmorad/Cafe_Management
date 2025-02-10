
import DeleteAllOrders from './DeleteAllOrders'
import ConfirmOrders from "./ConfirmOrders"
import { PDFDownloadLink } from '@react-pdf/renderer'
import CafeBillPDF from './GeneratePdf'
import { fetchProducts } from "@/state/products/productsSlice";
import useFetchProducts from '@/hooks/useFetchProdcts';
import useMemoizedData from '@/hooks/useMemoizedData';
import { removeAllOrders } from '@/state/products/productsSlice';
import { useDispatch } from 'react-redux';
const ConfigOrders=() =>{
  const { products, orders, error, status } = useFetchProducts('');

    const memoizedData = useMemoizedData(orders, true,products);
    const dispatch = useDispatch();

   
  
    const handleDeleteAllOrders = () => {
      dispatch(removeAllOrders()); // Dispatch action to delete all orders
    };
  


  return (
    <div className="flex items-center justify-center gap-4">
        <DeleteAllOrders 
                disable={orders.length === 0} 
                onDelete={handleDeleteAllOrders}/>
        <ConfirmOrders disable={orders.length === 0} 
          data={memoizedData}/>
        
    </div>
  )
}

export default ConfigOrders