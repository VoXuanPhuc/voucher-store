import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DetailVoucherComponent } from './detail-voucher/detail-voucher.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SellVoucherComponent } from './sell-voucher/sell-voucher.component';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
    data: {
      pageTitle: 'Home',
    },
  },

  {
    path: 'home',
    component: LandingPageComponent,
    data: {
      pageTitle: 'Home',
    },
  },

  {
    path: 'vouchers',
    component: SellVoucherComponent,
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
