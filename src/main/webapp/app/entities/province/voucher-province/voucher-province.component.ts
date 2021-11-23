import { ProvinceService } from 'app/entities/province/service/province.service';
import { IProvince } from 'app/entities/province/province.model';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-voucher-province',
  templateUrl: './voucher-province.component.html',
  styleUrls: ['./voucher-province.component.scss'],
})
export class VoucherProvinceComponent implements OnInit {
  provinces?: IProvince[];
  isLoading = false;

  constructor(private provinceService: ProvinceService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.provinceService.query().subscribe(
      res => {
        this.isLoading = true;
        this.provinces = res.body ?? [];
      },
      () => window.alert('An error occurs when loading data!')
    );
  }
}
