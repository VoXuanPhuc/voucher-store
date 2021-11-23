import { ServiceTypeService } from './../service/service-type.service';
import { Component, OnInit } from '@angular/core';
import { IServiceType } from '../service-type.model';
import { faFilm, faHeartbeat } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'jhi-voucher-service',
  templateUrl: './voucher-service.component.html',
  styleUrls: ['./voucher-service.component.scss'],
})
export class VoucherServiceComponent implements OnInit {
  serviceTypes?: IServiceType[];
  isLoading = false;

  constructor(private serviceTypeService: ServiceTypeService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  public loadAll(): void {
    this.serviceTypeService.query().subscribe(
      res => {
        this.isLoading = true;
        this.serviceTypes = res.body ?? [];
        window.console.log(this.serviceTypes);
      },
      error => window.console.log('An error occurred while loading data')
    );
  }
}
