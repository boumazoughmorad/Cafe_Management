import {createBrowserRouter, RouterProvider} from 'react-router-dom'
import App from '@/App';
import NotFoundPage from '@/pages/ErrorPages/NotFoundPage';
import HomePage from '@/pages/HomePage';
import OrdersPage from '@/pages/OrdersPage';
import SignInPage from '@/pages/SignInPage';
import Route from './Route';
import ProfilePage from '../pages/ProfilePage';
const router = createBrowserRouter([
    {
        path:'/',
        element: (
            <Route isProtected>
              <App />
            </Route>
          ),
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
            },
            {
              path:'/profile',
              element: <ProfilePage/>,
              errorElement: <NotFoundPage />,
          }
    
    ]
    },
    {
        path:'/SignIn',
        element: (
            <Route>
              <SignInPage />
            </Route>
          ),
    }
])

const Router = () => <RouterProvider router={router} />
export default Router;