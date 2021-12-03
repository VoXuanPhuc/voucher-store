import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
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
  constructor(
    private voucherServie: VoucherService,
    private route: ActivatedRoute,
    private storeService: StoreService,
    private voucherCodeService: VoucherCodeService
  ) {
    this.availableVoucher = 0;
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
      this.storeService.find(this.Voucher.event?.store?.id ?? 1).subscribe(resStore => {
        this.Voucher.event!.store = resStore.body;
      });
    });
  }
}
