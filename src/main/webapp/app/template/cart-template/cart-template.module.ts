import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CartTemplateRoutingModule } from './cart-template-routing.module';
import { CartPageComponent } from './cart-page/cart-page.component';
import { VoucherListComponent } from './voucher-list/voucher-list.component';
import { CheckoutComponent } from './checkout/checkout.component';
import { DiscountComponent } from './discount/discount.component';

@NgModule({
    declarations: [CartPageComponent, VoucherListComponent, CheckoutComponent, DiscountComponent],
    imports: [CommonModule, CartTemplateRoutingModule],
})
export class CartTemplateModule {}
