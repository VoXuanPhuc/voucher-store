import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { StoreComponent } from './list/store.component';
import { StoreDetailComponent } from './detail/store-detail.component';
import { StoreUpdateComponent } from './update/store-update.component';
import { StoreDeleteDialogComponent } from './delete/store-delete-dialog.component';
import { StoreRoutingModule } from './route/store-routing.module';
import { TopStoreComponent } from './top-store/top-store.component';
import { AllStoreComponent } from './all-store/all-store.component';

@NgModule({
  imports: [SharedModule, StoreRoutingModule],
  declarations: [
    StoreComponent,
    StoreDetailComponent,
    StoreUpdateComponent,
    StoreDeleteDialogComponent,
    TopStoreComponent,
    AllStoreComponent,
  ],
  entryComponents: [StoreDeleteDialogComponent],
  exports: [TopStoreComponent, AllStoreComponent],
})
export class StoreModule {}
