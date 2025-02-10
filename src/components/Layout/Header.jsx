import { Separator } from '@/components/ui';
import { useTheme } from '@/components/ThemeProvider/ThemeProvider';
import { Button } from '@/components/ui';
import { Link } from 'react-router-dom';
import { Moon, Sun } from 'lucide-react';

const Header = () => {
    const { theme, setTheme } = useTheme();

    return (
        <div className="z-10 px-8 py-4 right-0 left-0 top-0">
            <div className="flex flex-row items-center justify-between gap-8 bg-transparent">
                <Link to={'/'} className="">Home</Link>
                <Link to={'/OrdersPage'} className="">Orders Page</Link>

                {/* Toggle Theme Button */}
                <Button
                    onClick={() => setTheme(theme === 'dark' ? 'light' : 'dark')}
                    className='rounded-full h-auto w-auto'
                >
                    {theme === 'dark' ? (
                        <Sun className="h-4 w-4 transition-[var(--transition)]" />
                    ) : (
                        <Moon className="h-4 w-4 transition-[var(--transition)]" />
                    )}
                </Button>
            </div>
        </div>
    );
};

export { Header };
