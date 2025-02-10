import { CheckCircle } from "lucide-react";
import { Button } from "@/components/ui";
import { PDFDownloadLink } from "@react-pdf/renderer";
import CafeBillPDF from "./GeneratePdf";
import Swal from "sweetalert2";

const ConfirmOrders = ({ disable, data = [] }) => {

  const handleConfirmOrders = () => {
    Swal.fire({
      title: "<strong>Confirm Orders</strong>",
      icon: "success",
      html: "<p> confirm the orders and download the PDF</p>",

    });
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
