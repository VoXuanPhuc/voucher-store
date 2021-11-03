import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelationshipType } from '../relationship-type.model';
import { RelationshipTypeService } from '../service/relationship-type.service';
import { RelationshipTypeDeleteDialogComponent } from '../delete/relationship-type-delete-dialog.component';

@Component({
  selector: 'jhi-relationship-type',
  templateUrl: './relationship-type.component.html',
})
export class RelationshipTypeComponent implements OnInit {
  relationshipTypes?: IRelationshipType[];
  isLoading = false;

  constructor(protected relationshipTypeService: RelationshipTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.relationshipTypeService.query().subscribe(
      (res: HttpResponse<IRelationshipType[]>) => {
        this.isLoading = false;
        this.relationshipTypes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IRelationshipType): number {
    return item.id!;
  }

  delete(relationshipType: IRelationshipType): void {
    const modalRef = this.modalService.open(RelationshipTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.relationshipType = relationshipType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
