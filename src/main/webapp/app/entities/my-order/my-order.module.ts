import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MyOrderComponent } from './list/my-order.component';
import { MyOrderDetailComponent } from './detail/my-order-detail.component';
import { MyOrderUpdateComponent } from './update/my-order-update.component';
import { MyOrderDeleteDialogComponent } from './delete/my-order-delete-dialog.component';
import { MyOrderRoutingModule } from './route/my-order-routing.module';

@NgModule({
  imports: [SharedModule, MyOrderRoutingModule],
  declarations: [MyOrderComponent, MyOrderDetailComponent, MyOrderUpdateComponent, MyOrderDeleteDialogComponent],
  entryComponents: [MyOrderDeleteDialogComponent],
})
export class MyOrderModule {}
