import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetailVoucherComponent } from './detail-voucher/detail-voucher.component';
import { LandingPageComponent } from './landing-page/landing-page.component';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
    data: {
      pageTitle: 'Voucher',
    },
  },
  {
    path: 'detailVoucher',
    component: DetailVoucherComponent,
    data: {
      pageTitle: 'DetailVoucher',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TemplateRoutingModule {}
