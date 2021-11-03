import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherStatus } from '../voucher-status.model';
import { VoucherStatusService } from '../service/voucher-status.service';
import { VoucherStatusDeleteDialogComponent } from '../delete/voucher-status-delete-dialog.component';

@Component({
  selector: 'jhi-voucher-status',
  templateUrl: './voucher-status.component.html',
})
export class VoucherStatusComponent implements OnInit {
  voucherStatuses?: IVoucherStatus[];
  isLoading = false;

  constructor(protected voucherStatusService: VoucherStatusService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.voucherStatusService.query().subscribe(
      (res: HttpResponse<IVoucherStatus[]>) => {
        this.isLoading = false;
        this.voucherStatuses = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVoucherStatus): number {
    return item.id!;
  }

  delete(voucherStatus: IVoucherStatus): void {
    const modalRef = this.modalService.open(VoucherStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voucherStatus = voucherStatus;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
