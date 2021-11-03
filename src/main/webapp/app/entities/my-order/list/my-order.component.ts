import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyOrder } from '../my-order.model';
import { MyOrderService } from '../service/my-order.service';
import { MyOrderDeleteDialogComponent } from '../delete/my-order-delete-dialog.component';

@Component({
  selector: 'jhi-my-order',
  templateUrl: './my-order.component.html',
})
export class MyOrderComponent implements OnInit {
  myOrders?: IMyOrder[];
  isLoading = false;

  constructor(protected myOrderService: MyOrderService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.myOrderService.query().subscribe(
      (res: HttpResponse<IMyOrder[]>) => {
        this.isLoading = false;
        this.myOrders = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMyOrder): number {
    return item.id!;
  }

  delete(myOrder: IMyOrder): void {
    const modalRef = this.modalService.open(MyOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.myOrder = myOrder;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
