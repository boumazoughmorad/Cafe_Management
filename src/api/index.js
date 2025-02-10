import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { env } from '@/lib/env';
import {getListInstructions} from './instructionService'
import {getListProductCafe, getProductCafeById} from './productCafeService'
import {findOrderByIdProductAndIdUser, OrderNewProduct} from './OrderProuct'
import Orderdata from './data/OrdersData';


// Creates a base axios instance
const api = axios.create({
  baseURL: env.BASE_URL,
});



// Creates a mock adapter for axios
const adapter = new MockAdapter(api, { delayResponse: 1000 });


// ******************************Instructions************************************
// Gets all instructions
adapter.onGet('/api/instructions').reply(() => {
  const instructions = getListInstructions();  
  return [200, instructions];
});


// ***************************Product********************************************
// Gets all Produits
adapter.onGet('/api/Listproduits').reply((config) => {
  const { params } = config;
  
  const listProduits = getListProductCafe(params);  
  return [200, listProduits];
});


// Gets Product Product by id
adapter.onGet(/\/api\/product\/\d+/).reply(() => {
  const id = parseInt(config.url.match(/\/api\/listings\/(\d+)/)[1]);
  const product = getProductCafeById(id);
  if(!product) {
    return [404, {message: 'Product not found'}]
  }
  return [200, product];
});



// *********************Oredrs****************************************************
// 
adapter.onPost('/api/NewOrder').reply((config) => {


  
  const requestData = JSON.parse(config.data); 
  const id_product = requestData.prouductId;
  const id_user = requestData.iduser;
  const numberOfOrders = requestData.numberOfOrders;
  
  try{
    OrderNewProduct(id_product,id_user,numberOfOrders);
  }catch(error){
    return [404, {message: 'Probleme in Puch data', error}]
  }
  return [200, {message:"success"}];
});


// Mock GET Order By Product Id and User Id
adapter.onGet(/\/api\/getOrderbyIdProductAndIdUser\/(\d+)\/(\d+)/).reply((config) => {
  
  // Extract productId and iduser from the URL
  const [_, productId, iduser] = config.url.match(/\/api\/getOrderbyIdProductAndIdUser\/(\d+)\/(\d+)/);

  
  // Mock response logic
  try {
    const order = findOrderByIdProductAndIdUser(Number(productId), Number(iduser));
    if (order) {
      return [200, order]; // Return the order if found
    } else {
      return [200, 0]; // Return 404 if order not found
    }
  } catch (error) {
    return [500, { message: 'Internal server error', error }]; // Handle errors
  }
});



export default api;
