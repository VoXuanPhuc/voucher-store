import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyOrder } from '../my-order.model';
import { MyOrderService } from '../service/my-order.service';

@Component({
  templateUrl: './my-order-delete-dialog.component.html',
})
export class MyOrderDeleteDialogComponent {
  myOrder?: IMyOrder;

  constructor(protected myOrderService: MyOrderService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.myOrderService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
