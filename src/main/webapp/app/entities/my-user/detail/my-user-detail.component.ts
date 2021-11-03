import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMyUser } from '../my-user.model';

@Component({
  selector: 'jhi-my-user-detail',
  templateUrl: './my-user-detail.component.html',
})
export class MyUserDetailComponent implements OnInit {
  myUser: IMyUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ myUser }) => {
      this.myUser = myUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
