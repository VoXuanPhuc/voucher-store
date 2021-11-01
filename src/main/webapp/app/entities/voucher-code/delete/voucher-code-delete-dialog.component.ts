import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherCode } from '../voucher-code.model';
import { VoucherCodeService } from '../service/voucher-code.service';

@Component({
  templateUrl: './voucher-code-delete-dialog.component.html',
})
export class VoucherCodeDeleteDialogComponent {
  voucherCode?: IVoucherCode;

  constructor(protected voucherCodeService: VoucherCodeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherCodeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
