import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoucherImage } from '../voucher-image.model';
import { VoucherImageService } from '../service/voucher-image.service';
import { VoucherImageDeleteDialogComponent } from '../delete/voucher-image-delete-dialog.component';

@Component({
  selector: 'jhi-voucher-image',
  templateUrl: './voucher-image.component.html',
})
export class VoucherImageComponent implements OnInit {
  voucherImages?: IVoucherImage[];
  isLoading = false;

  constructor(protected voucherImageService: VoucherImageService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.voucherImageService.query().subscribe(
      (res: HttpResponse<IVoucherImage[]>) => {
        this.isLoading = false;
        this.voucherImages = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IVoucherImage): number {
    return item.id!;
  }

  delete(voucherImage: IVoucherImage): void {
    const modalRef = this.modalService.open(VoucherImageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voucherImage = voucherImage;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
