import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherComponent } from './list/voucher.component';
import { VoucherDetailComponent } from './detail/voucher-detail.component';
import { VoucherUpdateComponent } from './update/voucher-update.component';
import { VoucherDeleteDialogComponent } from './delete/voucher-delete-dialog.component';
import { VoucherRoutingModule } from './route/voucher-routing.module';
import { HotVoucherComponent } from './hot-voucher/hot-voucher.component';
import { SellVoucherComponent } from './sell-voucher/sell-voucher.component';

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
  exports: [HotVoucherComponent],
})
export class VoucherModule {}
