import { HttpClient } from '@angular/common/http';
import { Component, NgZone, OnDestroy, OnInit, } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { Order } from './domains/order';
import { OrdersReactiveService } from './services/orders-reactive.service';

@Component({
  selector: 'reactive-order-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  title = 'reactive-orders-ui';

  orders: Order[];
  sub: Subscription;

  constructor(private ordersReactiveService: OrdersReactiveService,
    private zone: NgZone, private http: HttpClient) { }

  ngOnInit() {
    this.orders = [];

    this.getSnapshot();

    this.sub = this.getOrderStream().subscribe({
      next: data => {
        this.addOrder(data);
      },
      error: err => console.error(err)
    });
  }
  
  getSnapshot() {
    this.http.get<Order[]>("http://localhost:8095/server-sent/snapshot").subscribe(result => {
      result.forEach(r => this.addOrder(r));
    });
  }

  addOrder(order: Order) {
    this.orders = [...this.orders, order];
  }
  
  getOrderStream(): Observable<Order> {
    return Observable.create(
      observer => {
        let source = new EventSource("http://localhost:8095/server-sent");
        source.onmessage = event => {
          let order: Order = JSON.parse(event.data);
          this.zone.run(() => {
            observer.next(order)
          })
        }
        source.onerror = event => {
          this.zone.run(() => {
            observer.error(event)
          })
        }
      }
    )
  }

  ngOnDestroy(): void {
    this.sub && this.sub.unsubscribe();
  }

}
