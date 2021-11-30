import { ServiceTypeService } from './../service/service-type.service';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
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
  selectedIndex?: number = -1;
  typeId?: number;

  @Output() typeChanged: EventEmitter<number> = new EventEmitter();

  constructor(private serviceTypeService: ServiceTypeService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  setIndex(index: number): void {
    this.selectedIndex = index;
  }

  trackId(index: number, item: IServiceType): number {
    return item.id!;
  }

  public loadAll(): void {
    this.serviceTypeService.query().subscribe(
      res => {
        this.isLoading = true;
        this.serviceTypes = res.body ?? [];
      },
      error => window.console.log('An error occurred while loading data ', error)
    );
  }

  onTypeChanged(id: number): void {
    this.typeId = id;
    this.typeChanged.emit(id);
  }
}
