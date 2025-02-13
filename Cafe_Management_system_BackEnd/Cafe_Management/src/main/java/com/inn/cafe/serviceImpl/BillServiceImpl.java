package com.inn.cafe.serviceImpl;

import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Bill;
import com.inn.cafe.contents.CafeConstants;
import com.inn.cafe.dao.BillDao;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;



    @Override
    public ResponseEntity<String> generatedReport(Map<String, Object> requestMap) {
        log.info("Inside generatedReport");
        try{

            String fileName;
                if (validateRequestMap(requestMap)) {

                      if(requestMap.containsKey("isGenerate") && !(Boolean) requestMap.get("isGenerate")){
                          fileName = (String) requestMap.get("uuid");
                      } else{
                          fileName = CafeUtils.getUUid();
                          requestMap.put("uuid",fileName);
                          insertBill(requestMap);
                      }

                      String data = "Name : "+requestMap.get("name")
                              + "\n" + "ConatctNumber : "+requestMap.get("contactNumber")
                              + "\n" + "Email : "+requestMap.get("email")
                              + "\n" + "Payment Mathode : "+requestMap.get("patment mathode");

                    Document document = new Document();
                    PdfWriter.getInstance(document,new FileOutputStream(CafeConstants.STORE_LOCATION+"\\"+fileName + ".pdf"));
                    document.open();

                    setRectangleInPof(document);

                    Paragraph chunck = new Paragraph("Cafe Managment System",getFont("Header"));
                    chunck.setAlignment(Element.ALIGN_CENTER);
                    document.add(chunck);

                    Paragraph paragraph =new Paragraph(data + "\n \n" ,getFont("Data"));
                    document.add(paragraph);

                    PdfPTable table = new PdfPTable(5);
                    table.setWidthPercentage(100);
                    addTableHeader(table);

                    JSONArray jsonArray =  CafeUtils.getJsonArrayFromString((String) requestMap.get("productDetails"));
                    for(int i = 0; i < jsonArray.length();i++){
                        addRows(table,CafeUtils.getMapFromJson(jsonArray.getString(i)));
                    }

                    document.add(table);

                    Paragraph footer = new Paragraph("Total : "+requestMap.get("total")+"\n" +
                            "Thank you for visiting Please visit again!!!",getFont("Data"));
                    document.add(footer);
                    document.close();


                      return  new ResponseEntity<>("{\"uuid\" : \""+fileName+"\"}",HttpStatus.OK);
                }

            return  CafeUtils.getResponseEntity("Requered data not found", HttpStatus.BAD_REQUEST);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();
       try{
            if(jwtFilter.isAdmin()){
                list = billDao.getAllBill();
            } else {
                list = billDao.getAllBillByUserName(jwtFilter.getCurrentUser());
            }
      } catch (Exception ex) {

            ex.printStackTrace();
        }
        return  new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf : requestMap  {}",requestMap);
        try{
            byte[] byteArray = new byte[0];
            if(!requestMap.containsKey("uuid")  && validateRequestMap(requestMap))
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);

            String filePath = CafeConstants.STORE_LOCATION +"\\"+ (String) requestMap.get("uuid") + ".pdf";
            if(CafeUtils.isFileExist(filePath)){
                byteArray = getByteArray(filePath);
                return  new ResponseEntity<>(byteArray,HttpStatus.OK);
            } else {
                requestMap.put("isGenerate",false);
                generatedReport(requestMap);
                byteArray = getByteArray(filePath);
                return  new ResponseEntity<>(byteArray,HttpStatus.OK);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            Optional optional =billDao.findById(id);
            if(!optional.isEmpty()) {
                billDao.deleteById(id);
                return  CafeUtils.getResponseEntity("Bill Deleted Successfully" , HttpStatus.OK);

            }
            return  CafeUtils.getResponseEntity("Bill id Does not exist" , HttpStatus.OK);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private byte[] getByteArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return  byteArray;
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));

    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Name","Category","Quantity","Price","Sub Total")
                .forEach(colmunTitle ->{
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.BLACK);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(colmunTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);

                });
    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type){
            case "Header" :
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return  headerFont;
            case "Data" :
                Font DataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                DataFont.setStyle(Font.BOLD);
                return  DataFont;
            default:
                return new Font();

        }
    }

    private void setRectangleInPof(Document document) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);

        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);



    }

    private void insertBill(Map<String, Object> requestMap) {
        log.info("Insitite insertBill requestMap {}",requestMap);

        try {

            Bill bill=new Bill();
            bill.setName((String) requestMap.get("name"));
            bill.setEmail((String) requestMap.get("email"));
            bill.setUuid((String) requestMap.get("uuid"));
            bill.setContactNumber((String) requestMap.get("contactNumber"));
            bill.setPaymentmethode((String) requestMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) requestMap.get("total")));
            bill.setProductDetail((String) requestMap.get("productDetails"));
            bill.setCreatedBy(jwtFilter.getCurrentUser());
            log.info("Insitite insertBill bill{}",bill);

            billDao.save(bill);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> requestMap) {
        log.info("Instide into validateRequestMap");
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("paymentMethod") &&
                requestMap.containsKey("productDetails") &&
                requestMap.containsKey("total") ;


    }
}
