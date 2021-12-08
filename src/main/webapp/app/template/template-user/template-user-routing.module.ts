import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileUserComponent } from './profile-user/profile-user.component';
import { TemplateUserComponent } from './template-user/template-user.component';

const router: Routes = [
  {
    path: '',
    component: TemplateUserComponent,
    data: {
      pageTile: 'My Profile',
    },
  },
];

@NgModule({
  imports: [RouterModule.forChild(router)],
  exports: [RouterModule],
})
export class TemplateUserRoutingModule {}
