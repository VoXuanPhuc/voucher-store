import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EditProfileComponent } from './edit-profile/edit-profile.component';
import { ProfileUserComponent } from './profile-user/profile-user.component';
import { TemplateUserComponent } from './template-user/template-user.component';
import { VoucherUserComponent } from './voucher-user/voucher-user.component';

const router: Routes = [
    {
        path: '',
        component: TemplateUserComponent,
        children: [
            {
                path: '',
                component: ProfileUserComponent,
            },
            {
                path: 'profile',
                component: ProfileUserComponent,
                data: {
                    pageTile: 'My Profile',
                },
            },
            {
                path: 'edit-profile',
                component: EditProfileComponent,
                data: {
                    pageTile: 'Edit Profile',
                },
            },
        ],
    },
];

@NgModule({
    imports: [RouterModule.forChild(router)],
    exports: [RouterModule],
})
export class TemplateUserRoutingModule {}
