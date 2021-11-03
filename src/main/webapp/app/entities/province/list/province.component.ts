import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProvince } from '../province.model';
import { ProvinceService } from '../service/province.service';
import { ProvinceDeleteDialogComponent } from '../delete/province-delete-dialog.component';

@Component({
  selector: 'jhi-province',
  templateUrl: './province.component.html',
})
export class ProvinceComponent implements OnInit {
  provinces?: IProvince[];
  isLoading = false;

  constructor(protected provinceService: ProvinceService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.provinceService.query().subscribe(
      (res: HttpResponse<IProvince[]>) => {
        this.isLoading = false;
        this.provinces = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IProvince): number {
    return item.id!;
  }

  delete(province: IProvince): void {
    const modalRef = this.modalService.open(ProvinceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.province = province;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
