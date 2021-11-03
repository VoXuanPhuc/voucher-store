import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IStoreUser } from '../store-user.model';
import { StoreUserService } from '../service/store-user.service';
import { StoreUserDeleteDialogComponent } from '../delete/store-user-delete-dialog.component';

@Component({
  selector: 'jhi-store-user',
  templateUrl: './store-user.component.html',
})
export class StoreUserComponent implements OnInit {
  storeUsers?: IStoreUser[];
  isLoading = false;

  constructor(protected storeUserService: StoreUserService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.storeUserService.query().subscribe(
      (res: HttpResponse<IStoreUser[]>) => {
        this.isLoading = false;
        this.storeUsers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IStoreUser): number {
    return item.id!;
  }

  delete(storeUser: IStoreUser): void {
    const modalRef = this.modalService.open(StoreUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.storeUser = storeUser;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
