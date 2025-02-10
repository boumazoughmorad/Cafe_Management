import { Facebook, Instagram, Linkedin, Twitter, Globe, MessageCircle } from 'lucide-react';
import {
    Separator,
  } from '@/components/ui';
const Footer = () => {
    return (
        <div className="flex flex-col justify-center items-center mt-10 ">
            <Separator className='my-4' />
            <div className="flex space-x-9 py-10">
                <a href='#' className='bg-white rounded-full w-12 h-12 flex items-center justify-center transition-[var(--transition)] hover:w-16 hover:h-16 hover:opacity-70'>
                    <Facebook className="w-6 h-6 text-black  " />
                </a>
                 <a href='#' className='bg-white rounded-full w-12 h-12 flex items-center justify-center transition-[var(--transition)] hover:w-16 hover:h-16 hover:opacity-70'>
                    <Instagram className="w-6 h-6 text-black  " />
                </a>
                 <a href='#' className='bg-white rounded-full w-12 h-12 flex items-center justify-center transition-[var(--transition)] hover:w-16 hover:h-16 hover:opacity-70'>
                    <MessageCircle className="w-6 h-6 text-black  " />
                </a>
                 <a href='#' className='bg-white rounded-full w-12 h-12 flex items-center justify-center transition-[var(--transition)] hover:w-16 hover:h-16 hover:opacity-70'>
                    <Linkedin className="w-6 h-6 text-black  " />
                </a>
                <a href='#' className='bg-white rounded-full w-12 h-12 flex items-center justify-center transition-[var(--transition)] hover:w-16 hover:h-16 hover:opacity-70'>
                    <Globe className="w-6 h-6 text-black  " />
                </a>
                <a href='#' className='bg-white rounded-full w-12 h-12 flex items-center justify-center transition-[var(--transition)] hover:w-16 hover:h-16 hover:opacity-70'>
                    <Twitter className="w-6 h-6 text-black  " />
                </a>
                
            </div>

            <div className="text-center  text-sm mt-4 py-6 dark:bg-black w-full ">
                    © {new Date().getFullYear()} Morad Bouamzough. All rights reserved.
                    
            </div>
        </div>
    )
}

export  { Footer };