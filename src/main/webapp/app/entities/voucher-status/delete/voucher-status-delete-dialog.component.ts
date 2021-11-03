import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherStatus } from '../voucher-status.model';
import { VoucherStatusService } from '../service/voucher-status.service';

@Component({
  templateUrl: './voucher-status-delete-dialog.component.html',
})
export class VoucherStatusDeleteDialogComponent {
  voucherStatus?: IVoucherStatus;

  constructor(protected voucherStatusService: VoucherStatusService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherStatusService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
