import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStoreUser } from '../store-user.model';

@Component({
  selector: 'jhi-store-user-detail',
  templateUrl: './store-user-detail.component.html',
})
export class StoreUserDetailComponent implements OnInit {
  storeUser: IStoreUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ storeUser }) => {
      this.storeUser = storeUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
