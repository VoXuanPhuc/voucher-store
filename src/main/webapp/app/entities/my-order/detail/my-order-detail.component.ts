import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyOrder } from '../my-order.model';

@Component({
  selector: 'jhi-my-order-detail',
  templateUrl: './my-order-detail.component.html',
})
export class MyOrderDetailComponent implements OnInit {
  myOrder: IMyOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myOrder }) => {
      this.myOrder = myOrder;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
