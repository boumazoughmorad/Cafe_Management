import { Trash2 } from "lucide-react"
import { Button } from "@/components/ui"



const DeleteAllOrders = ({ disable, onDelete }) =>{
    return (
        <Button className="flex gap-2 "      
         onClick={onDelete}
        disabled={disable}
        >
            <Trash2 />
            <span>Delete All</span>
        </Button>
    )
}

export default DeleteAllOrders;