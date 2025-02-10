import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import App from './App';
import NotFoundPage from './pages/ErrorPages/NotFoundPage';
import HomePage from './pages/HomePage';
import OrdersPage from './pages/OrdersPage';
const router = createBrowserRouter([
    {
        path:'/',
        element: <App />,
        errorElement: <NotFoundPage />,
        children:[{
                path:'/',
                element: <HomePage />,
                errorElement: <NotFoundPage />,
            },
            {
                path:'/OrdersPage',
                element: <OrdersPage />,
                errorElement: <NotFoundPage />,
            }
    
    ]
    }
])

const Router = () => <RouterProvider router={router} />
export default Router;