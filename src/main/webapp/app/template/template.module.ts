import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TemplateRoutingModule } from './template-routing.module';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherModule } from 'app/entities/voucher/voucher.module';
import { CategoryModule } from 'app/entities/category/category.module';
import { ServiceTypeModule } from 'app/entities/service-type/service-type.module';
import { OtherModule } from 'app/entities/others/other.module';
import { StoreModule } from 'app/entities/store/store.module';

@NgModule({
  declarations: [LandingPageComponent],
  imports: [CommonModule, TemplateRoutingModule, SharedModule, VoucherModule, ServiceTypeModule, CategoryModule, OtherModule, StoreModule],
})
export class TemplateModule {}
