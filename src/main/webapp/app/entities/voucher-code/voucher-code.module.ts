import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherCodeComponent } from './list/voucher-code.component';
import { VoucherCodeDetailComponent } from './detail/voucher-code-detail.component';
import { VoucherCodeUpdateComponent } from './update/voucher-code-update.component';
import { VoucherCodeDeleteDialogComponent } from './delete/voucher-code-delete-dialog.component';
import { VoucherCodeRoutingModule } from './route/voucher-code-routing.module';

@NgModule({
  imports: [SharedModule, VoucherCodeRoutingModule],
  declarations: [VoucherCodeComponent, VoucherCodeDetailComponent, VoucherCodeUpdateComponent, VoucherCodeDeleteDialogComponent],
  entryComponents: [VoucherCodeDeleteDialogComponent],
})
export class VoucherCodeModule {}
