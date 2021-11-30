import { Value } from './../../../admin/metrics/metrics.model';
import { ProvinceService } from 'app/entities/province/service/province.service';
import { IProvince } from 'app/entities/province/province.model';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'jhi-voucher-province',
  templateUrl: './voucher-province.component.html',
  styleUrls: ['./voucher-province.component.scss'],
})
export class VoucherProvinceComponent implements OnInit {
  provinces?: IProvince[];
  isLoading = false;

  form?: FormGroup;

  @Output() checkboxChanged: EventEmitter<Array<number>> = new EventEmitter();

  constructor(private provinceService: ProvinceService, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      province: this.formBuilder.array([], [Validators.required]),
    });
  }

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

  onCheckboxChange(event: any): void {
    const province: FormArray = this.form?.get('province') as FormArray;

    if (event.target.checked) {
      province.push(new FormControl(event.target.value));
    } else {
      const index = province.controls.findIndex(x => x.value === event.target.value);
      province.removeAt(index);
    }

    const provinceIds = new Array(0);

    for (let i = 0; i < province.length; i++) {
      provinceIds.push(province.at(i).value);
    }

    this.checkboxChanged.emit(provinceIds);
  }
}
