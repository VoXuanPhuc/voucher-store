import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { MyUserComponent } from './list/my-user.component';
import { MyUserDetailComponent } from './detail/my-user-detail.component';
import { MyUserUpdateComponent } from './update/my-user-update.component';
import { MyUserDeleteDialogComponent } from './delete/my-user-delete-dialog.component';
import { MyUserRoutingModule } from './route/my-user-routing.module';
import { EditUserComponent } from './edit-user/edit-user.component';

@NgModule({
    imports: [SharedModule, MyUserRoutingModule],
    declarations: [MyUserComponent, MyUserDetailComponent, MyUserUpdateComponent, MyUserDeleteDialogComponent, EditUserComponent],
    entryComponents: [MyUserDeleteDialogComponent],
    exports: [EditUserComponent],
})
export class MyUserModule {}
