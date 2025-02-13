import {Card, CardContent, Button} from '@/components/ui'
import {  Coffee,  DollarSign,  RemoveFormatting, Trash2} from 'lucide-react';
import { Link } from 'react-router-dom';
import { OrderForm } from '@/components/FormsComponents';
import { useDispatch } from 'react-redux';
import { removeOrder } from '../../../state/products/productsSlice';
const ProduitCafeCard = (props) =>{
    const dispatch = useDispatch();

    const handleRemoveOrder=()=>{
 
        dispatch(removeOrder(props?.product.id))
      
    }
    return (
        <Link>
        <Card className='w-[320px] h-[400px] relative'>
            <div className="relative h-auto">
              
               <span className='
               absolute w-20 h-20 bg-white backdrop-blur-sm rounded-full rounded-br-none right-0  bottom-0 flex items-center justify-center shadow-xl shadow-black text-black font-bold '>
                <DollarSign className='h-4 w-4 ' />
                {props?.product.price}
                </span>
                <img 
                    className='object-cover h-[200px] w-full '
                    src={props?.product.imageUrl}
                />
                
            </div>
            <CardContent className='flex flex-col gap-2 p-4 text-center'>
               
                <h2 className='mb-2 text-xl font-semibold'>
                    
                    {props?.product.name}
                </h2>
                
                {
                    props?.isPageOrder==true ? (
                       
                            <Button  className='flex items center w-full text-center' onClick={handleRemoveOrder}>
                                <Trash2 className="w-5 h-5 hover:scale-110"  />
                             </Button>


                    ):(
                        <OrderForm prouductId={props?.product.id} isExist={props?.product.isExist}/>

                    )
                }
                <div className='flex items-center gap-2'>
                    <Coffee className='h-4 w-4 text-primary' />
                    <span className='text-muted-foreground flex gap-5'>
                        <span className='font-bold'>Type : </span>
                        <span  className=''>{props?.product.type}</span>
                    </span>
                </div>
                <div className='flex  items-center gap-2'>
                    
                    <span className='text-muted-foreground text-[12px]'>
                        {props?.product.description}
                        
                    </span>
                </div>
                
            </CardContent>
        </Card>
        </Link>
        
    )
}



export default ProduitCafeCard;