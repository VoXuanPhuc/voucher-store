import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherImageComponent } from './list/voucher-image.component';
import { VoucherImageDetailComponent } from './detail/voucher-image-detail.component';
import { VoucherImageUpdateComponent } from './update/voucher-image-update.component';
import { VoucherImageDeleteDialogComponent } from './delete/voucher-image-delete-dialog.component';
import { VoucherImageRoutingModule } from './route/voucher-image-routing.module';

@NgModule({
  imports: [SharedModule, VoucherImageRoutingModule],
  declarations: [VoucherImageComponent, VoucherImageDetailComponent, VoucherImageUpdateComponent, VoucherImageDeleteDialogComponent],
  entryComponents: [VoucherImageDeleteDialogComponent],
})
export class VoucherImageModule {}
