export const calculeTotalAllOrders= (data=[])=>{
  let Total=0
  for(let i=0; i<=data.length;i++){
    Total+=(data[0]?.price*data[0]?.numberOfOrders)
  }
  return Total?.toFixed(2)
}
