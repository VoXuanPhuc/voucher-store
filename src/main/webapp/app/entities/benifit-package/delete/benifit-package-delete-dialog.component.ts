import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IBenifitPackage } from '../benifit-package.model';
import { BenifitPackageService } from '../service/benifit-package.service';

@Component({
  templateUrl: './benifit-package-delete-dialog.component.html',
})
export class BenifitPackageDeleteDialogComponent {
  benifitPackage?: IBenifitPackage;

  constructor(protected benifitPackageService: BenifitPackageService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.benifitPackageService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
