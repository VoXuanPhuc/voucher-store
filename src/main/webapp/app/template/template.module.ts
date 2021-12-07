import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { CategoryModule } from 'app/entities/category/category.module';
import { FeedbackModule } from 'app/entities/feedback/feedback.module';
import { OtherModule } from 'app/entities/others/other.module';
import { ProvinceModule } from 'app/entities/province/province.module';
import { ServiceTypeModule } from 'app/entities/service-type/service-type.module';
import { StoreModule } from 'app/entities/store/store.module';
import { VoucherModule } from 'app/entities/voucher/voucher.module';
import { SharedModule } from 'app/shared/shared.module';
import { AllStoresComponent } from './all-stores/all-stores.component';
import { DetailVoucherComponent } from './detail-voucher/detail-voucher.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SellVoucherComponent } from './sell-voucher/sell-voucher.component';
import { TemplateRoutingModule } from './template-routing.module';

import { ProfileUserComponent } from './profile-user/profile-user.component';

@NgModule({
  declarations: [LandingPageComponent, SellVoucherComponent, DetailVoucherComponent, ProfileUserComponent, AllStoresComponent],
  imports: [
    CommonModule,
    TemplateRoutingModule,
    SharedModule,
    VoucherModule,
    ServiceTypeModule,
    CategoryModule,
    OtherModule,
    StoreModule,
    ProvinceModule,
    FeedbackModule,
    StoreModule,
    SharedModule,
  ],
})
export class TemplateModule {}
