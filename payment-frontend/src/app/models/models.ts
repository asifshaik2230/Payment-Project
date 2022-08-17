export interface Customer {
    accountNumber: string;
    clearBalance: number;
    customerName: string;
    overdraft: boolean;
  }
  export interface TransferTypeCode {
    value: string;
    name: string;
  }
  export interface MessageType {
    messageCode: string;
    description: string;
  }
  export interface Bank {
    bic: string;
    name: string;
  }
  export interface CurrencyType {
    code: string;
    name: string;
    value: number;
    symbol: string;
  }
  export interface TransactionRequest {
    payload: {
      customerId: string;
      amount: number;
      messageCode: string;
      receiverAccountNumber: string;
      receiverAccountName: string;
      receiverBIC: string;
      transferTypeCode: 'C' | 'O'
    }
  }
  export interface ErrorResponse{
    message:string,
    description:string
  }
  