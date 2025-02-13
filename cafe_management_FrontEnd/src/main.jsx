import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import ThemeProvider from './components/ThemeProvider/ThemeProvider.jsx'
import Router from './Route/Router.jsx'
import { Provider } from 'react-redux'
import { store } from '@/state/store.js'
import AuthProvider from '@/components/AuthPrivider/AuthProvider.jsx'

createRoot(document.getElementById('root')).render(
   <ThemeProvider>
      <Provider store={store}>
      <AuthProvider>
         <Router />
         </AuthProvider>
      </Provider>
   </ThemeProvider>
)
