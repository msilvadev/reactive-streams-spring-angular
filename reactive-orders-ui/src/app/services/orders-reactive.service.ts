import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, NgZone, OnDestroy } from '@angular/core';
import { Observable, Subject, Subscription } from 'rxjs';
import { Order } from '../domains/order';
//const EventSource: any = window['EventSource'];

@Injectable({
  providedIn: 'root'
})
export class OrdersReactiveService {
  url: string = 'http://localhost:8095/server-sent'
  eventSource: EventSource;

  //orders: Array<Order> = new Array<Order>();
  orders: Order[] = [];

  constructor(private _zone: NgZone, private http: HttpClient) { }

  getSnapshotOrders(): Order[] {
    this.http.get<Order[]>(this.url+"/snapshot").subscribe(result => {
      result.forEach(r => this.orders.push(r));
    });
    return this.orders;
  }

  // getOrderStream() {
  //   return Observable.create((observer) => {
  //     this.eventSource = new EventSource(this.url);
  //     this.eventSource.onmessage = (event) => {
  //       let json: Order = JSON.parse(event.data);
  //       this.orders.push(json);

  //       this._zone.run(() => {
  //         observer.next(this.orders)
  //       })
  //     }
  //     this.eventSource.onerror = (error) => {
  //       if (this.eventSource.readyState === 0) {
  //         console.log('The stream has been closed by the server.')

  //       } else {
  //         this._zone.run(() => {
  //           observer.error('EventSource error: ' + error)
  //         })
  //       }
  //     }
  //   })
  // }

  removeEventEmitter() {
    let params: HttpParams = new HttpParams();
    params.set("name", "matheus");
    this.http.delete(this.url, { params: params });
  }

}
