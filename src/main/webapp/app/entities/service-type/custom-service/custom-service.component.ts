import { Component, OnInit } from '@angular/core';
import { ServiceTypeService } from '../service/service-type.service';

@Component({
  selector: 'jhi-custom-service',
  templateUrl: './custom-service.component.html',
  styleUrls: ['./custom-service.component.scss'],
})
export class CustomServiceComponent implements OnInit {
  constructor(protected serviceTypeService: ServiceTypeService) {}

  ngOnInit(): void {
    return;
  }
}
