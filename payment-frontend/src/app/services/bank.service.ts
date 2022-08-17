import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Bank, Customer, MessageType, TransferTypeCode } from '../models/models';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  constructor(private http:HttpClient) { }
  getCustomerData(cid: string): Observable<Customer> {
    return this.http.get<Customer>(environment.BASE_URL+'api/customer/'+cid);
  }
  getBankDetails(bic:string):Observable<Bank>{
    return this.http.get<Bank>(environment.BASE_URL+'api/bank/'+bic);
  }
  getTransferTypeCodes(): Observable<TransferTypeCode[]> {
    return of([
      {value: 'C', name: 'Customer Transfer'},
      {value: 'O', name: 'Bank Transfer of Own'}
    ])
  }
  getMessageTypeCodes(): Observable<MessageType[]> {
    return this.http.get<MessageType[]>(environment.BASE_URL+"api/messageCode");
  }
  transactionRequest(transactionRequest:any): Observable<any> {
    console.log(transactionRequest)
    return this.http.post<any>(environment.BASE_URL+"api/transaction", transactionRequest).pipe(
      map(res => {
        console.log(JSON.stringify(res))
        return res;
      })
    )
  }

}
