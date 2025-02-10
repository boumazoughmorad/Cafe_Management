import Orderdata from './data/OrdersData'

export const OrderNewProduct = (id_product,id_user,numberOfOrders)=>{
    //  Find the highest id in ALldata
  const maxId = Orderdata.length > 0 ? Math.max(...Orderdata.map(item => item.id)) : 0;

  
  const Newdata ={
    id : maxId,
    idProduct: id_product,
    idUser: id_user,
    numberOfOrders:numberOfOrders
  } 

  try{
    //  Add the new data to ALldata
    Orderdata.push(Newdata);
  }catch(error){
    console.log(error);
    
  }
  
}

// Example function to find an order
export const  findOrderByIdProductAndIdUser=(productId, iduser) =>{
 
    
    if(Orderdata.length > 0){
     
        return Orderdata.find(
            (order) => order?.productId === productId && order?.iduser === iduser
          );
    }
    return ;
  }