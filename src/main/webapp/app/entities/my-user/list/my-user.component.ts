import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMyUser } from '../my-user.model';
import { MyUserService } from '../service/my-user.service';
import { MyUserDeleteDialogComponent } from '../delete/my-user-delete-dialog.component';

@Component({
  selector: 'jhi-my-user',
  templateUrl: './my-user.component.html',
})
export class MyUserComponent implements OnInit {
  myUsers?: IMyUser[];
  isLoading = false;

  constructor(protected myUserService: MyUserService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.myUserService.query().subscribe(
      (res: HttpResponse<IMyUser[]>) => {
        this.isLoading = false;
        this.myUsers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMyUser): number {
    return item.id!;
  }

  delete(myUser: IMyUser): void {
    const modalRef = this.modalService.open(MyUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.myUser = myUser;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
