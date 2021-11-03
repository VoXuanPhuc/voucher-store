import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyUser } from '../my-user.model';
import { MyUserService } from '../service/my-user.service';

@Component({
  templateUrl: './my-user-delete-dialog.component.html',
})
export class MyUserDeleteDialogComponent {
  myUser?: IMyUser;

  constructor(protected myUserService: MyUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.myUserService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
