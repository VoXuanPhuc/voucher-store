import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGift } from '../gift.model';

@Component({
  selector: 'jhi-gift-detail',
  templateUrl: './gift-detail.component.html',
})
export class GiftDetailComponent implements OnInit {
  gift: IGift | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gift }) => {
      this.gift = gift;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
