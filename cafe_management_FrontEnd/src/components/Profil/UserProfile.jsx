import { BarChart2, Edit2, LayoutDashboard, LayoutDashboardIcon } from "lucide-react"
import CoverImage from "./CoverImage"
import { useAuth } from '@/components/AuthPrivider/AuthProvider';
import ProfileImage from "./ProfileImage";

const  UserProfile=() =>{
    const { user } = useAuth();
  return (
    <div >
              
                <CoverImage />
        
      
            <ProfileImage  userImage={user?.image}/>

     
       
        <div className=" ">
            <div className="text-center px-14">
               
                <h2 className=" text-3xl font-bold">{user?.name}</h2>
                <p className="mt-2 text-gray-500 text-sm">Welcome! We hope you are satisfied and that the application has made ordering easier for you. Our goal is always to ensure our customers’ happiness. </p>
            </div>
            <hr className="mt-6" />
            <div className="flex  bg-background ">
                <div className="flex flex-row items-center justify-center w-1/2 p-4 hover:bg-gray-100 hover:text-black transition-[--var(transition)] cursor-pointer">
                   
                    <span className="font-semibold "> <Edit2 /> </span> 
                </div>
                <div className="border"></div>
                <div className="flex flex-row items-center justify-center w-1/2 p-4 hover:bg-gray-100 hover:text-black transition-[--var(transition)] cursor-pointer">
                    <p> <span className="font-semibold"><BarChart2/> </span></p>
                </div>

            </div>
        </div>

       
    </div>
  )
}

export default UserProfile