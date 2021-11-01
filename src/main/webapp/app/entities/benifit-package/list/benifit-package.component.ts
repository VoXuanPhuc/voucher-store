import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBenifitPackage } from '../benifit-package.model';
import { BenifitPackageService } from '../service/benifit-package.service';
import { BenifitPackageDeleteDialogComponent } from '../delete/benifit-package-delete-dialog.component';

@Component({
  selector: 'jhi-benifit-package',
  templateUrl: './benifit-package.component.html',
})
export class BenifitPackageComponent implements OnInit {
  benifitPackages?: IBenifitPackage[];
  isLoading = false;

  constructor(protected benifitPackageService: BenifitPackageService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.benifitPackageService.query().subscribe(
      (res: HttpResponse<IBenifitPackage[]>) => {
        this.isLoading = false;
        this.benifitPackages = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IBenifitPackage): number {
    return item.id!;
  }

  delete(benifitPackage: IBenifitPackage): void {
    const modalRef = this.modalService.open(BenifitPackageDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.benifitPackage = benifitPackage;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
