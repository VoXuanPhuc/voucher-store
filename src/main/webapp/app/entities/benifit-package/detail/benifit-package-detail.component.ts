import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBenifitPackage } from '../benifit-package.model';

@Component({
  selector: 'jhi-benifit-package-detail',
  templateUrl: './benifit-package-detail.component.html',
})
export class BenifitPackageDetailComponent implements OnInit {
  benifitPackage: IBenifitPackage | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ benifitPackage }) => {
      this.benifitPackage = benifitPackage;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
