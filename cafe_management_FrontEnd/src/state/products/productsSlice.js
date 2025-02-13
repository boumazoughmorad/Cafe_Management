import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

import api from '@/api';

const initialState = {
  products: [],
  error: null,
  orders:[],
  status: 'idle',
};

const productsSlice = createSlice({
    name: 'products',
    initialState,
    reducers: {
      addNewOrder: (state, action) => {
        
        const { prouductId, numberOfOrders } = action.payload;


        const existingOrderIndex = state.orders.findIndex((order) => 
          String(order.prouductId) === String(prouductId)
        );

        
        if (existingOrderIndex !== -1) {
          
          // Use `map` to create a new array (Redux best practice)
          state.orders = state.orders.map((order, index) => 
            index === existingOrderIndex 
              ? { ...order, numberOfOrders: order.numberOfOrders + numberOfOrders } 
              : order
          );
        } else {
          state.orders.push(action.payload);
        }
      },
      removeOrder: (state, action) => {
        
        state.orders = state.orders.filter((order) => order.prouductId !== action.payload);
      },
      removeAllOrders: (state) => {
        state.orders = [];
      },
    },
    extraReducers(builder) {
        builder
          .addCase(fetchProducts.pending, (state) => {
            state.status = 'loading';
          })
          .addCase(fetchProducts.fulfilled, (state, action) => {
            state.status = 'succeeded';
            
            
            
            state.products = action.payload;
          })
          .addCase(fetchProducts.rejected, (state, action) => {
            const message = action.error.message;
    
            if (message !== 'Aborted') {
              state.status = 'failed';
              state.error = message;
            }
          });
      },
})

export const fetchProducts = createAsyncThunk(
    'products/fetchProducts',
    async (options) => {
        
        
        const response = await api.get('/api/Listproduits',options);
        return response.data;
      },
)
export const { addNewOrder, removeOrder, removeAllOrders } =
  productsSlice.actions;
export  default productsSlice.reducer;