import { StyleSheet } from "@react-pdf/renderer";

// Define styles
 export const styles = StyleSheet.create({
    page: {
      flexDirection: 'column',
      backgroundColor: '#FFFFFF',
      padding: 30,
    },
    header: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginBottom: 20,
      borderBottom: '1px solid #e0e0e0',
      paddingBottom: 10,
    },
    logo: {
      width: 100,
      height: 50,
    },
    title: {
      fontSize: 24,
      fontWeight: 'bold',
      color: '#333333',
    },
    subtitle: {
      fontSize: 14,
      color: '#666666',
    },
    table: {
      display: 'table',
      width: '100%',
      borderStyle: 'solid',
      borderWidth: 1,
      borderColor: '#e0e0e0',
      marginBottom: 20,
    },
    tableRow: {
      flexDirection: 'row',
      borderBottomWidth: 1,
      borderBottomColor: '#e0e0e0',
      alignItems: 'center',
      padding: 10,
    },
    tableHeader: {
      backgroundColor: '#f5f5f5',
      fontWeight: 'bold',
    },
    tableCell: {
      flex: 1,
      fontSize: 12,
      color: '#333333',
      textAlign: 'center',
    },
    footer: {
      flexDirection: 'row',
      justifyContent: 'flex-end',
      marginTop: 20,
      paddingTop: 10,
      borderTop: '1px solid #e0e0e0',
    },
    totalText: {
      fontSize: 16,
      fontWeight: 'bold',
      color: '#333333',
    },
  });
