import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { EventService } from 'app/entities/event/service/event.service';
import { StoreService } from 'app/entities/store/service/store.service';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IVoucher, Voucher } from 'app/entities/voucher/voucher.model';

@Component({
  selector: 'jhi-detail-voucher',
  templateUrl: './detail-voucher.component.html',
  styleUrls: ['./detail-voucher.component.scss'],
})
export class DetailVoucherComponent implements OnInit {
  Voucher!: IVoucher;
  availableVoucher: any;
  id: any;
  selectedVoucher: number;
  constructor(
    private voucherServie: VoucherService,
    private route: ActivatedRoute,
    private storeService: StoreService,
    private voucherCodeService: VoucherCodeService,
    private eventService: EventService
  ) {
    this.availableVoucher = 0;
    this.selectedVoucher = 1;
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.id = params.get('id');
      this.loadVoucherById();
      this.voucherCodeService.countVoucherCode(this.id).subscribe(res => (this.availableVoucher = res.body));
    });
  }

  loadVoucherById(): void {
    this.voucherServie.find(this.id).subscribe(res => {
      this.Voucher = res.body!;
      this.eventService.find(this.Voucher.event?.id ?? 1).subscribe(resSer => {
        this.Voucher.event = resSer.body;
        this.storeService.find(this.Voucher.event?.store?.id ?? 5).subscribe(resStore => {
          this.Voucher.event!.store = resStore.body;
        });
      });
    });
  }

  increment(): void {
    if (this.selectedVoucher >= this.availableVoucher) {
      return;
    }
    this.selectedVoucher = this.selectedVoucher + 1;
  }
  decrement(): void {
    if (this.selectedVoucher === 1) {
      return;
    }
    this.selectedVoucher = this.selectedVoucher - 1;
  }
}
