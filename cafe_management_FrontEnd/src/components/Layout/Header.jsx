import { Separator } from '@/components/ui';
import { useTheme } from '@/components/ThemeProvider/ThemeProvider';
import { Button } from '@/components/ui';
import { Link } from 'react-router-dom';
import { Home, Moon, ShoppingBasket, Sun } from 'lucide-react';

const Header = () => {
    const { theme, setTheme } = useTheme();

    return (
        <div className="z-10 px-12 py-4 right-0 left-0 top-0">
            <div className="flex flex-row items-center justify-between gap-8 bg-transparent">

                <div className="">
                    <Link to={'/'} className="">
                        <Home/>
                    </Link>
                </div>
                <div className="flex items-center justify-center">

                        <Button
                            onClick={() => setTheme(theme === 'dark' ? 'light' : 'dark')}
                            className='rounded-full h-auto w-auto px-4'
                        >
                            {theme === 'dark' ? (
                                <Sun className="h-4 w-4 transition-[var(--transition)]" />
                            ) : (
                                <Moon className="h-4 w-4 transition-[var(--transition)]" />
                            )}
                        </Button>
                        <Link to={'/OrdersPage'} className="px-4">
                        <ShoppingBasket/>
                        </Link>

                        {/* Toggle Theme Button */}


                        <Link to={'/profile'} className='px-4'>
                            <img className="h-10 w-10 rounded-full   " 
                            src="https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=2000&q=80" 
                            alt="" />
                        </Link>
                </div>

            </div>
        </div>
    );
};

export { Header };
