import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceType } from '../service-type.model';

@Component({
  selector: 'jhi-service-type-detail',
  templateUrl: './service-type-detail.component.html',
})
export class ServiceTypeDetailComponent implements OnInit {
  serviceType: IServiceType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceType }) => {
      this.serviceType = serviceType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
