import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StoreUserComponent } from './list/store-user.component';
import { StoreUserDetailComponent } from './detail/store-user-detail.component';
import { StoreUserUpdateComponent } from './update/store-user-update.component';
import { StoreUserDeleteDialogComponent } from './delete/store-user-delete-dialog.component';
import { StoreUserRoutingModule } from './route/store-user-routing.module';

@NgModule({
  imports: [SharedModule, StoreUserRoutingModule],
  declarations: [StoreUserComponent, StoreUserDetailComponent, StoreUserUpdateComponent, StoreUserDeleteDialogComponent],
  entryComponents: [StoreUserDeleteDialogComponent],
})
export class StoreUserModule {}
