import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AllStoresComponent } from './all-stores/all-stores.component';
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
        path: ':id/Voucher',
        component: DetailVoucherComponent,
        data: {
            pageTitle: 'DetailVoucher',
        },
    },
    {
        path: 'stores',
        component: AllStoresComponent,
        data: {
            pageTitle: 'Stores',
        },
    },
    {
        path: 'user',
        data: {
            pageTitle: 'My Profile',
        },
        loadChildren: () => import('./template-user/template-user.module').then(m => m.TemplateUserModule),
    },
    {
        path: 'cart',
        data: {
            pageTitle: 'Cart',
        },
        loadChildren: () => import('./cart-template/cart-template.module').then(m => m.CartTemplateModule),
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class TemplateRoutingModule {}
