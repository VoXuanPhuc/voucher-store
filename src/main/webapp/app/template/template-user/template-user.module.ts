import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { DateFormatPipe } from 'app/entities/date-format.pipe';
import { MyUserModule } from 'app/entities/my-user/my-user.module';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ProfileUserComponent } from './profile-user/profile-user.component';
import { SildebarUserComponent } from './sildebar-user/sildebar-user.component';
import { TemplateUserRoutingModule } from './template-user-routing.module';
import { TemplateUserComponent } from './template-user/template-user.component';
import { VoucherUserComponent } from './voucher-user/voucher-user.component';

@NgModule({
    declarations: [
        ProfileUserComponent,
        TemplateUserComponent,
        SildebarUserComponent,
        VoucherUserComponent,
        EditProfileComponent,
        DateFormatPipe,
    ],
    imports: [CommonModule, TemplateUserRoutingModule, MyUserModule],
})
export class TemplateUserModule {}
