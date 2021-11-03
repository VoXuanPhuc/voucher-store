import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrderStatus } from '../order-status.model';
import { OrderStatusService } from '../service/order-status.service';
import { OrderStatusDeleteDialogComponent } from '../delete/order-status-delete-dialog.component';

@Component({
  selector: 'jhi-order-status',
  templateUrl: './order-status.component.html',
})
export class OrderStatusComponent implements OnInit {
  orderStatuses?: IOrderStatus[];
  isLoading = false;

  constructor(protected orderStatusService: OrderStatusService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.orderStatusService.query().subscribe(
      (res: HttpResponse<IOrderStatus[]>) => {
        this.isLoading = false;
        this.orderStatuses = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IOrderStatus): number {
    return item.id!;
  }

  delete(orderStatus: IOrderStatus): void {
    const modalRef = this.modalService.open(OrderStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.orderStatus = orderStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
