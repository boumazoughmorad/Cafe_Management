import { Mouse } from "lucide-react";

const WelcomeMessage = () => {
  return (
    <div className="w-full dark:bg-white sm:h-[260px]  h-[280px] dark:text-black text-2xl text-center font-bold flex items-center justify-center px-20 gap-5 text-white bg-gray-500">
        <div className="md:text-[60px] font-serif sm:text-[30px] text-[20px]  ">
        Welcome 
        
        </div>
        <div className="font-mono flex flex-col items-center justify-center md:text-[28px] sm:text-[14px] text-[10px] ">
            <p>Welcome to our Cafe Management System! We're thrilled to have you here and look forward to serving you the best experience.</p>
            <a href="#listProductsCafe">
            <Mouse className=" translate-mouse-y" />

            </a>

        </div>
        
    </div>
  )
}

export default WelcomeMessage ;