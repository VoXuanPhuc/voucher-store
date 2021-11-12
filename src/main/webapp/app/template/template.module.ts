import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TemplateRoutingModule } from './template-routing.module';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { SharedModule } from 'app/shared/shared.module';
import { VoucherModule } from 'app/entities/voucher/voucher.module';
import { CategoryModule } from 'app/entities/category/category.module';

@NgModule({
  declarations: [LandingPageComponent],
  imports: [CommonModule, TemplateRoutingModule, SharedModule, VoucherModule, CategoryModule],
})
export class TemplateModule {}
