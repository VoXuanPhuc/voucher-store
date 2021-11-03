import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGift } from '../gift.model';
import { GiftService } from '../service/gift.service';
import { GiftDeleteDialogComponent } from '../delete/gift-delete-dialog.component';

@Component({
  selector: 'jhi-gift',
  templateUrl: './gift.component.html',
})
export class GiftComponent implements OnInit {
  gifts?: IGift[];
  isLoading = false;

  constructor(protected giftService: GiftService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.giftService.query().subscribe(
      (res: HttpResponse<IGift[]>) => {
        this.isLoading = false;
        this.gifts = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IGift): number {
    return item.id!;
  }

  delete(gift: IGift): void {
    const modalRef = this.modalService.open(GiftDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.gift = gift;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
