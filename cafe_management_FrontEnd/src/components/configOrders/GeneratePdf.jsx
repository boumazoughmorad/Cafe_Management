import React from 'react';
import { Page, Text, View, Document, Image } from '@react-pdf/renderer';

import {styles} from './StyleCafeBillPdf'
import { calculeTotalAllOrders } from '@/lib/utils/claculateTotalOrders';



// PDF Component
const CafeBillPDF = ({billData=[]}) => (
  
  
  
  <Document>
    <Page size="A4" style={styles.page}>
      {/* Header */}
      <View style={styles.header}>
        <View>
          <Text style={styles.title}>cafe Managment System</Text>
          <Text style={styles.subtitle}>address :---------------</Text>
          <Text style={styles.subtitle}>Phone: 000000000000</Text>
        </View>
        <View>
          <Text style={styles.subtitle}>Bill No: 60</Text>
          <Text style={styles.subtitle}>Date: 2025-07-13</Text>
        </View>
      </View>

      {/* Table Header */}
      <View style={[styles.tableRow, styles.tableHeader]}>
        <Text style={styles.tableCell}>Item</Text>
        <Text style={styles.tableCell}>Quantity</Text>
        <Text style={styles.tableCell}>Price</Text>
        <Text style={styles.tableCell}>Total</Text>
      </View>

      {/* Table Rows */}
      {(Array.isArray(billData) && billData.map((item, index) => (
        <View key={index} style={styles.tableRow}>
          <Text style={styles.tableCell}>{item?.name}</Text>
          <Text style={styles.tableCell}>{item?.numberOfOrders}</Text>
          <Text style={styles.tableCell}>
            ${item?.price}
            </Text>
          <Text style={styles.tableCell}>
            ${(item?.numberOfOrders * item?.price)}
            </Text>
        </View>
      )))}

      {/* Footer */}
      <View style={styles.footer}>
        <Text style={styles.totalText}>Total: 
          {/* ${billData?.total.toFixed(2)} */}
          ${calculeTotalAllOrders(billData)}
          </Text>
      </View>
      <Text style={{ textAlign: "center", marginTop: 10 }}>Thank you for visiting our café! ☕</Text>
    </Page>
  </Document>
);

export default CafeBillPDF;