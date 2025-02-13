import InstructionsCard from "@/components/ListAndItem/Instructions/InstructionsCard";
import ListingProduitCafe from "@/components/ListAndItem/ProduitCafe/ListingProduitCafe";
import WelcomeMessage from "@/components/screen/WelcomeMessage ";
import { useMemo } from "react";

const HomePage = () =>{
    const memoizedInstructionsCard = useMemo(() => <InstructionsCard  />, []);

    return (
        <div className="">
            <div className="">
                {memoizedInstructionsCard}
            </div>
            <div className="">
                <WelcomeMessage />
            </div>
            <div className="">
                    
                <div className="">
                    <ListingProduitCafe   isPageOrder={false}/>
                </div>
            </div>
           
        </div>
    );
}

export default HomePage;