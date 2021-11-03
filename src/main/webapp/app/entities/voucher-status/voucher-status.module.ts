import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherStatusComponent } from './list/voucher-status.component';
import { VoucherStatusDetailComponent } from './detail/voucher-status-detail.component';
import { VoucherStatusUpdateComponent } from './update/voucher-status-update.component';
import { VoucherStatusDeleteDialogComponent } from './delete/voucher-status-delete-dialog.component';
import { VoucherStatusRoutingModule } from './route/voucher-status-routing.module';

@NgModule({
  imports: [SharedModule, VoucherStatusRoutingModule],
  declarations: [VoucherStatusComponent, VoucherStatusDetailComponent, VoucherStatusUpdateComponent, VoucherStatusDeleteDialogComponent],
  entryComponents: [VoucherStatusDeleteDialogComponent],
})
export class VoucherStatusModule {}
