import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherDeleteDialogComponent } from './delete/voucher-delete-dialog.component';
import { VoucherDetailComponent } from './detail/voucher-detail.component';
import { HotVoucherComponent } from './hot-voucher/hot-voucher.component';
import { VoucherComponent } from './list/voucher.component';
import { VoucherRoutingModule } from './route/voucher-routing.module';
import { SellVoucherComponent } from './sell-voucher/sell-voucher.component';
import { VoucherUpdateComponent } from './update/voucher-update.component';

@NgModule({
  imports: [SharedModule, VoucherRoutingModule],
  declarations: [
    VoucherComponent,
    VoucherDetailComponent,
    VoucherUpdateComponent,
    VoucherDeleteDialogComponent,
    HotVoucherComponent,
    SellVoucherComponent,
  ],
  entryComponents: [VoucherDeleteDialogComponent],
  exports: [HotVoucherComponent, SellVoucherComponent],
})
export class VoucherModule {}
