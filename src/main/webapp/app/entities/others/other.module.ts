import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OtherRoutingModule } from './other-routing.module';
import { SharedModule } from 'app/shared/shared.module';
import { SlideComponent } from './slide/slide.component';

@NgModule({
  declarations: [SlideComponent],
  imports: [CommonModule, SharedModule, OtherRoutingModule],
})
export class OtherModule {}
