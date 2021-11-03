import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';
import { VoucherDeleteDialogComponent } from '../delete/voucher-delete-dialog.component';

@Component({
  selector: 'jhi-voucher',
  templateUrl: './voucher.component.html',
})
export class VoucherComponent implements OnInit {
  vouchers?: IVoucher[];
  isLoading = false;

  constructor(protected voucherService: VoucherService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.voucherService.query().subscribe(
      (res: HttpResponse<IVoucher[]>) => {
        this.isLoading = false;
        this.vouchers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVoucher): number {
    return item.id!;
  }

  delete(voucher: IVoucher): void {
    const modalRef = this.modalService.open(VoucherDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voucher = voucher;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
