import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileUserComponent } from './profile-user/profile-user.component';
import { TemplateUserRoutingModule } from './template-user-routing.module';
import { TemplateUserComponent } from './template-user/template-user.component';
import { SildebarUserComponent } from './sildebar-user/sildebar-user.component';
import { VoucherUserComponent } from './voucher-user/voucher-user.component';

@NgModule({
  declarations: [ProfileUserComponent, TemplateUserComponent, SildebarUserComponent, VoucherUserComponent],
  imports: [CommonModule, TemplateUserRoutingModule],
})
export class TemplateUserModule {}
