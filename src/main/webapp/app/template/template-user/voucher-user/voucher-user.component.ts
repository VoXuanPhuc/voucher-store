import { Component, OnInit } from '@angular/core';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { MyOrderService } from 'app/entities/my-order/service/my-order.service';
import { StoreService } from 'app/entities/store/service/store.service';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { IOurPagination } from 'app/shared/our-pagination/pagination.model';

@Component({
  selector: 'jhi-voucher-user',
  templateUrl: './voucher-user.component.html',
  styleUrls: ['./voucher-user.component.scss'],
})
export class VoucherUserComponent implements OnInit {
  voucherCodePaging?: IOurPagination;
  voucherCodes?: IVoucherCode[];
  private limit = 3;
  constructor(
    private voucherCodeService: VoucherCodeService,
    private voucherService: VoucherService,
    private orderService: MyOrderService,
    private eventService: EventService,
    private storeService: StoreService
  ) {}

  ngOnInit(): void {
    this.loadAllVoucherCode(1, this.limit);
  }

  pageChangedHandler(page: number): void {
    this.loadAllVoucherCode(page, this.limit);
  }

  loadAllVoucherCode(page: number, limit: number): void {
    this.voucherCodeService.queryWithPagingOfUser(page, limit).subscribe(
      data => {
        this.voucherCodePaging = data.body ?? [];
        this.voucherCodes = this.voucherCodePaging?.items ?? [];
        this.loadVoucherAndOrder();

        window.console.log(this.voucherCodes);
      },
      () => {
        window.alert('loi load voucher code');
      }
    );
  }

  loadVoucherAndOrder(): void {
    this.voucherCodes?.forEach(voucherCode => {
      this.findOrder(voucherCode);
      this.findVoucher(voucherCode);
    });
  }

  findOrder(voucheCode: IVoucherCode): void {
    this.orderService.find(voucheCode.order?.id ?? 0).subscribe(data => (voucheCode.order = data.body));
  }

  findVoucher(voucherCode: IVoucherCode): void {
    this.voucherService.find(voucherCode.voucher?.id ?? 0).subscribe(data => {
      voucherCode.voucher = data.body;
      this.findEvent(voucherCode.voucher!);
    });
  }

  findEvent(voucher: IVoucher): void {
    this.eventService.find(voucher.event?.id ?? 0).subscribe(data => {
      voucher.event = data.body;
      this.findStore(voucher.event!);
    });
  }

  findStore(event: IEvent): void {
    this.storeService.find(event.store?.id ?? 0).subscribe(data => {
      event.store = data.body;
    });
  }

  isAvailable(statusId: number): boolean {
    return statusId !== 2;
  }
}
