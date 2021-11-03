import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherImage } from '../voucher-image.model';
import { VoucherImageService } from '../service/voucher-image.service';

@Component({
  templateUrl: './voucher-image-delete-dialog.component.html',
})
export class VoucherImageDeleteDialogComponent {
  voucherImage?: IVoucherImage;

  constructor(protected voucherImageService: VoucherImageService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voucherImageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
