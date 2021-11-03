import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServiceType } from '../service-type.model';
import { ServiceTypeService } from '../service/service-type.service';
import { ServiceTypeDeleteDialogComponent } from '../delete/service-type-delete-dialog.component';

@Component({
  selector: 'jhi-service-type',
  templateUrl: './service-type.component.html',
})
export class ServiceTypeComponent implements OnInit {
  serviceTypes?: IServiceType[];
  isLoading = false;

  constructor(protected serviceTypeService: ServiceTypeService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.serviceTypeService.query().subscribe(
      (res: HttpResponse<IServiceType[]>) => {
        this.isLoading = false;
        this.serviceTypes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IServiceType): number {
    return item.id!;
  }

  delete(serviceType: IServiceType): void {
    const modalRef = this.modalService.open(ServiceTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.serviceType = serviceType;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
