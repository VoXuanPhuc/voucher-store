import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherCode } from '../voucher-code.model';
import { VoucherCodeService } from '../service/voucher-code.service';
import { VoucherCodeDeleteDialogComponent } from '../delete/voucher-code-delete-dialog.component';

@Component({
  selector: 'jhi-voucher-code',
  templateUrl: './voucher-code.component.html',
})
export class VoucherCodeComponent implements OnInit {
  voucherCodes?: IVoucherCode[];
  isLoading = false;

  constructor(protected voucherCodeService: VoucherCodeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.voucherCodeService.query().subscribe(
      (res: HttpResponse<IVoucherCode[]>) => {
        this.isLoading = false;
        this.voucherCodes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVoucherCode): number {
    return item.id!;
  }

  delete(voucherCode: IVoucherCode): void {
    const modalRef = this.modalService.open(VoucherCodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voucherCode = voucherCode;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
