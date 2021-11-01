import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { BenifitPackageComponent } from './list/benifit-package.component';
import { BenifitPackageDetailComponent } from './detail/benifit-package-detail.component';
import { BenifitPackageUpdateComponent } from './update/benifit-package-update.component';
import { BenifitPackageDeleteDialogComponent } from './delete/benifit-package-delete-dialog.component';
import { BenifitPackageRoutingModule } from './route/benifit-package-routing.module';

@NgModule({
  imports: [SharedModule, BenifitPackageRoutingModule],
  declarations: [
    BenifitPackageComponent,
    BenifitPackageDetailComponent,
    BenifitPackageUpdateComponent,
    BenifitPackageDeleteDialogComponent,
  ],
  entryComponents: [BenifitPackageDeleteDialogComponent],
})
export class BenifitPackageModule {}
