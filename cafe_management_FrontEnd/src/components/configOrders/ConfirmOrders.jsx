import { CheckCircle } from "lucide-react";
import { Button } from "@/components/ui";
import { PDFDownloadLink } from "@react-pdf/renderer";
import CafeBillPDF from "./GeneratePdf";
import Swal from "sweetalert2";
import api from "@/api";
import { useDispatch } from "react-redux";
import { removeAllOrders } from '@/state/products/productsSlice';
const ConfirmOrders = ({ disable, data = [] }) => {
  const dispatch = useDispatch();
  const handleConfirmOrders = async () => {
    try {
      // Save orders to the backend
      const result = await saveAllOrders(data);
  
      // If the API call is successful, show a success message
      if (result === "success") {
        dispatch(removeAllOrders());
        Swal.fire({
          title: "<strong>Confirm Orders</strong>",
          icon: "success",
          html: "<p>Orders confirmed successfully. You can now download the PDF.</p>",
        });
      } else {
        // Handle other cases (e.g., API returns a different status)
        Swal.fire({
          title: "<strong>Error</strong>",
          icon: "error",
          html: "<p>Failed to confirm orders. Please try again.</p>",
        });
      }
    } catch (error) {
      // Handle API errors
      console.error("Error saving orders:", error);
      Swal.fire({
        title: "<strong>Error</strong>",
        icon: "error",
        html: "<p>An error occurred while confirming orders. Please try again.</p>",
      });
    }
  };
  
  // API call to save all orders
  const saveAllOrders = async (data = []) => {
    try {
      const response = await api.post("/api/addAllOrders", data); // Use POST instead of GET
  
      // Check if the request was successful
      if (response.status === 200 || response.status === 201) {
        return "success";
      } else {
        return "failed";
      }
    } catch (error) {
      console.error("Error saving orders:", error);
      throw error; // Re-throw the error to handle it in the calling function
    }
  };

  return (
    <PDFDownloadLink document={<CafeBillPDF billData={data} />} fileName="cafe_bill.pdf">
      {({ loading }) => (
        <Button className="flex gap-2" disabled={disable} onClick={handleConfirmOrders}>
          <CheckCircle />
          <span>{loading ? "Generating..." : "Confirm All"}</span>
        </Button>
      )}
    </PDFDownloadLink>
  );
};

export default ConfirmOrders;
