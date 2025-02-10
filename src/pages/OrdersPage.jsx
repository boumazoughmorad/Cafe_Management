
import ListingProduitCafe from "@/components/ListAndItem/ProduitCafe/ListingProduitCafe";
import ConfigOrders from "@/components/configOrders/ConfigOrders";

const OrdersPage=() =>{

  return (
    <div className="mt-10">
          <ConfigOrders />
          <ListingProduitCafe id="listOrders"   isPageOrder={true} />
    </div>
  )
}

export default OrdersPage