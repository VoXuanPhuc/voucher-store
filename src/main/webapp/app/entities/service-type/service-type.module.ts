import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ServiceTypeComponent } from './list/service-type.component';
import { ServiceTypeDetailComponent } from './detail/service-type-detail.component';
import { ServiceTypeUpdateComponent } from './update/service-type-update.component';
import { ServiceTypeDeleteDialogComponent } from './delete/service-type-delete-dialog.component';
import { ServiceTypeRoutingModule } from './route/service-type-routing.module';

@NgModule({
  imports: [SharedModule, ServiceTypeRoutingModule],
  declarations: [ServiceTypeComponent, ServiceTypeDetailComponent, ServiceTypeUpdateComponent, ServiceTypeDeleteDialogComponent],
  entryComponents: [ServiceTypeDeleteDialogComponent],
})
export class ServiceTypeModule {}
