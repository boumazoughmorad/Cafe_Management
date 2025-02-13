import { ShoppingCart } from "lucide-react";
import { Stepper , Button } from "@/components/ui";
import {  useState } from "react";
import {useForm} from 'react-hook-form';
import { useDispatch } from "react-redux";
import { addNewOrder } from "@/state/products/productsSlice";
const OrderForm = ({prouductId, prouductisExist}) =>{

    const dispatch = useDispatch();
    const isExist = prouductisExist;
    const [numberOfOrders,setNumberOfOrders] = useState(0)
   
    const {
      register,
      handleSubmit,
      formState: { errors },
      setError,
    }=useForm({ })

    // submit fonction
    const onSubmit = async (dataSubmit) => {
        try {
          const data={
            prouductId:prouductId,
            numberOfOrders:numberOfOrders
          }
          
         
          dispatch(addNewOrder(data));
          
          setNumberOfOrders(0)
        } catch (e) {
          console.log("Error ",e);
          
          setError('root', "Error ",e);
         
          
        }
      };


    return (    
        <form action="">
            <div className="flex border rounded-lg items-center justify-between pr-3">
                    <Stepper {...register('numberOfOrders', { valueAsNumber: true })} label='Number' 
                    value={numberOfOrders} onChange={setNumberOfOrders}/>
                    
                    <Button className="rounded-none" size='icon'  disabled={isExist} onClick={handleSubmit(onSubmit)}>
                        <ShoppingCart className='hover:scale-110'/>

                    </Button>

            </div>
            
          
        </form>
        )

}

export { OrderForm };