import { Component, OnInit } from '@angular/core';
import {  Observable, of } from 'rxjs';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalConfig } from '@ng-bootstrap/ng-bootstrap';
import {  CurrencyType, ErrorResponse, MessageType, TransactionRequest, TransferTypeCode } from 'src/app/models/models';
import { BankService } from 'src/app/services/bank.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})


export class HomeComponent implements OnInit {
  showComponent!: boolean;
  error: ErrorResponse | null = null;
  sender!: UntypedFormGroup;
  receiver!: UntypedFormGroup;
  employee!: UntypedFormGroup
  transferTypeCodeList: Observable<TransferTypeCode[]> = of([]);
  messageCodes: Observable<MessageType[]> = of([])
  currencyTypes: CurrencyType[] = [
    { code: 'EUR', name: 'Euro', value: 84, symbol: '€' },
    { code: 'GBP', name: 'Great British Pound', value: 102, symbol: '£' },
    { code: 'INR', name: 'Indian Rupees', value: 1, symbol: '₹' },
    { code: 'JPY', name: 'Japanese Yen', value: 1, symbol: '¥' },
    { code: 'USD', name: 'US Dollar', value: 74, symbol: '$' }
  ]

  constructor(private fb: UntypedFormBuilder, config: NgbModalConfig, private modalService: NgbModal, private data: BankService) {
    const date = new Date();
    console.log(date.getDay())
    this.showComponent = date.getDay() !== 6 || date.getDay() !== 0;
    this.sender = fb.group({
      accountNumber: ['', [Validators.required, Validators.maxLength(14), Validators.minLength(14)]],
      customerName: [{ value: '', disabled: true }],
      clearBalance: [{ value: '', disabled: true }],
      senderBIC: [{ value: 'HDFCINBBAHM', disabled: true }]
    })
    this.receiver = fb.group({
      receiverAccountNumber: ['', [Validators.required, Validators.minLength(14), Validators.maxLength(14)]],
      receiverAccountName: ['', Validators.required],
      receiverBIC: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]],
      receiverBankName: [{ value: '', disabled: true }]
    });
    this.employee = fb.group({
      transferTypeCode: ['', Validators.required],
      messageCode: ['', Validators.required],
      amount: ['', Validators.required],
      totalAmount: [{ value: 0, disabled: true }]
    })
  }

  ngOnInit(): void {
    this.transferTypeCodeList = this.data.getTransferTypeCodes();
    this.messageCodes = this.data.getMessageTypeCodes();
  }
  fetchDetails(content: any): any {
    let accNumber: string = this.sender.value.accountNumber
    accNumber.length === 14 && this.data.getCustomerData(accNumber).subscribe(value => {
      console.log("succ");
      console.log(JSON.stringify(value))
      this.sender.get('customerName')?.setValue(value.customerName);
      this.sender.get('clearBalance')?.setValue(value.clearBalance);
    }, (err) => {
      console.log(err);
      this.error = {
        message: err.error.message,
        description: err.error.description
      };
      this.modalService.open(content)
    });
  }
  fetchBIC(content: any): any {
    let receiverBankBIC: string = this.receiver.value.receiverBIC
    receiverBankBIC.length === 11 && this.data.getBankDetails(receiverBankBIC).subscribe(value => {
      console.log(JSON.stringify(value))
      this.receiver.get('receiverBankName')?.setValue(value.name);
    }, err => {
      this.error = {
        message: err.error.message,
        description: err.error.description
      };
      this.modalService.open(content)
    })
  }

  getCurrencyItem(currency: string) {
    return this.currencyTypes.find(val => val.code == currency) || this.currencyTypes[2];
  }
  updateINR(currencyType: string) {

    this.employee.get('totalAmount')?.setValue(this.getCurrencyItem(currencyType).value * this.employee.value.amount)
  }


  submit(content: any) {
    console.log(this.employee.get('totalAmount')?.value)
    const {
      accountNumber = '',
      amount = 0.0,
      messageCode = 'SUC',
      receiverAccountName = '',
      receiverAccountNumber = '',
      receiverBIC = '',
      transferTypeCode = ''
    } = { ...this.employee.value, amount: this.employee.get('totalAmount')?.value, ...this.sender.value, ...this.receiver.value }
    const transactionRequest: TransactionRequest = {
      payload: {
        customerId: accountNumber,
        amount,
        messageCode,
        receiverAccountName,
        receiverAccountNumber,
        receiverBIC,
        transferTypeCode
      }
    }
    this.data.transactionRequest(transactionRequest.payload).subscribe(val => {
      this.error = {
        message: val.message,
        description: val.description
      }
      this.modalService.open(content)
      this.sender.reset();
      this.receiver.reset();
      this.employee.reset();
      // this.snack.open('Transaction is Successful', 'Dismiss', {
      //   duration: 1500
      // })
    }, err => {
      this.error = {
        message: err.error.message,
        description: err.error.description
      };
      this.sender.reset();
      this.receiver.reset();
      this.employee.reset();
      this.modalService.open(content)
      console.log(err)
    })
  }

}
