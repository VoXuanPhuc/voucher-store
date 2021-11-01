import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceType } from '../service-type.model';
import { ServiceTypeService } from '../service/service-type.service';

@Component({
  templateUrl: './service-type-delete-dialog.component.html',
})
export class ServiceTypeDeleteDialogComponent {
  serviceType?: IServiceType;

  constructor(protected serviceTypeService: ServiceTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
