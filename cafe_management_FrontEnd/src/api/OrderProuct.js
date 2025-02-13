import Orderdata from './data/OrdersData'

export const orderNewProduct = (id_product,id_user,numberOfOrders)=>{
    //  Find the highest id in ALldata
    console.log("hi");
    
  const maxId = Orderdata.length > 0 ? Math.max(...Orderdata.map(item => item.id)) : 0;
  console.log("hello");
  
  
  const Newdata ={
    id : maxId,
    idProduct: id_product,
    idUser: id_user,
    numberOfOrders:numberOfOrders
  } 
    console.log("new Data");
    
  try{
    //  Add the new data to ALldata
    Orderdata.push(Newdata);
  }catch(error){
    console.log(error);
    
  }
  
}

export const addAllOrders = (data=[])=>{

  console.log("data in addAllOrders is : ", data);

  Array.isArray(data) && data.map((order)=>{
    console.log("order is : ", order);
    console.log("data?.numberOfOrders is : ", order?.numberOfOrders);
    console.log("data?.id is : ", order?.prouductId);
      try{
        orderNewProduct(order?.prouductId,0,order?.numberOfOrders);
      } catch(error){
        console.log("error in saved order index:", index);
        
      }
  })
  // for(let index=0;index<=data.length;index++){

  // }
  console.log('data after confirm : ',Orderdata);
  return "success"
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