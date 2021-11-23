import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
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
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TemplateRoutingModule {}
