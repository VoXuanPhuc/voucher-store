import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStoreUser } from '../store-user.model';
import { StoreUserService } from '../service/store-user.service';

@Component({
  templateUrl: './store-user-delete-dialog.component.html',
})
export class StoreUserDeleteDialogComponent {
  storeUser?: IStoreUser;

  constructor(protected storeUserService: StoreUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.storeUserService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
