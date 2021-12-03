import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OtherRoutingModule } from './other-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { SlideComponent } from './slide/slide.component';
import { PriceRangeComponent } from './price-range/price-range.component';
import { VoucherSortComponent } from './voucher-sort/voucher-sort.component';

@NgModule({
  declarations: [SlideComponent, PriceRangeComponent, VoucherSortComponent],
  imports: [CommonModule, SharedModule, OtherRoutingModule],
  exports: [SlideComponent, PriceRangeComponent, VoucherSortComponent],
})
export class OtherModule {}
