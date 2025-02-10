import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import ThemeProvider from './components/ThemeProvider/ThemeProvider.jsx'
import Router from './Router.jsx'
import { Provider } from 'react-redux'
import { store } from '@/state/store.js'

createRoot(document.getElementById('root')).render(
   <ThemeProvider>
      <Provider store={store}>
         <Router />
      </Provider>
   </ThemeProvider>
)
