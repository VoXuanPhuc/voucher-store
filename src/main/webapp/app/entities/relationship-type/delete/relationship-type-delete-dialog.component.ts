import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelationshipType } from '../relationship-type.model';
import { RelationshipTypeService } from '../service/relationship-type.service';

@Component({
  templateUrl: './relationship-type-delete-dialog.component.html',
})
export class RelationshipTypeDeleteDialogComponent {
  relationshipType?: IRelationshipType;

  constructor(protected relationshipTypeService: RelationshipTypeService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.relationshipTypeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
