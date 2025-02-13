import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';
import { env } from '@/lib/env';
import {getListInstructions} from './instructionService'
import {getListProductCafe, getProductCafeById} from './productCafeService'
import {addAllOrders, findOrderByIdProductAndIdUser, orderNewProduct} from './OrderProuct'

import {
  withAuth,
} from './helpers';
import {  checkToken, signin } from './usersService';


// Creates a base axios instance
const api = axios.create({
  baseURL: env.BASE_URL,
  withCredentials: true,
});



// Creates a mock adapter for axios
const adapter = new MockAdapter(api, { delayResponse: 1000 });


// ******************************Instructions************************************
// Gets all instructions
adapter.onGet('/api/instructions').reply(() =>{
  // withAuth(async (config) =>{

  const instructions = getListInstructions();  
  return [200, instructions];
});


// ***************************Product********************************************
// Gets all Produits
adapter.onGet('/api/Listproduits').reply((config) =>{
  // withAuth(async (config) =>{
  const { params } = config;
  
  const listProduits = getListProductCafe(params);  
  return [200, listProduits];
});


// Gets Product Product by id
adapter.onGet(/\/api\/product\/\d+/).reply(async (config) => {
  // withAuth(async (config) => {
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
  // withAuth(async (config) => {


  
  const requestData = JSON.parse(config.data); 
  const id_product = requestData.prouductId;
  const id_user = requestData.iduser;
  const numberOfOrders = requestData.numberOfOrders;
  
  try{
    orderNewProduct(id_product,id_user,numberOfOrders);
  }catch(error){
    return [404, {message: 'Probleme in Puch data', error}]
  }
  return [200, {message:"success"}];
});



adapter.onPost('/api/addAllOrders').reply((config)=>{
  // withAuth(async (config) => {


  
  const requestData = JSON.parse(config.data); 
  console.log("requestData : ",requestData);
  
  
  try{
    addAllOrders(requestData);
  }catch(error){
    console.log("error");
    
    return [404, {message: 'Probleme in Puch data', error}]
  }
  return [200, {message:"success"}];
});


// Mock GET Order By Product Id and User Id
adapter.onGet(/\/api\/getOrderbyIdProductAndIdUser\/(\d+)\/(\d+)/).reply(async (config) => {
  // withAuth(async (config) => {
  
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



// Signs the user in
adapter.onPost('/api/signin').reply(async (config) => {
  const { data } = config;

  try {
    const response = await signin(JSON.parse(data));
    
    // Returns access token and user
    return [
      200,
      {
         user:response.user,
         token:response.token
      },
    ];
  } catch(error) {
    return [401, { message: 'Invalid credentials' }];
  }
});



adapter.onGet('/api/checkToken').reply(async (config)=>{
  try {


    
        // Simulate a token validation check
        const check = await checkToken(config?.token);
    
        const isValid = (check==="Token is valid")
  
        
        if (isValid) {
          return [200, { checkToken: true }];
        } else {
          return [401, { message: 'Invalid or expired token' }];
        }
    
 
   
  } catch(error) {
    console.log("Error validating token: ",error);

    return [401, { message: 'Error validating token:' }];
  }

  // withAuth(() => {
  //   // Removes refresh token from cookies


  //   return [200];
  // }),
});


// Signs the user out
adapter.onPost('/api/signout').reply(
  withAuth(() => {
    // Removes refresh token from cookies


    return [200];
  }),
);

export default api;


