import { StoreService } from 'app/entities/store/service/store.service';
import { Component, OnInit } from '@angular/core';
import { IStore } from 'app/entities/store/store.model';
import { IOurPagination } from 'app/shared/our-pagination/pagination.model';

@Component({
  selector: 'jhi-all-stores',
  templateUrl: './all-stores.component.html',
  styleUrls: ['./all-stores.component.scss'],
})
export class AllStoresComponent implements OnInit {
  pagination?: IOurPagination;
  stores?: IStore[];

  private limit = 3;

  constructor(private storeSerivce: StoreService) {}

  ngOnInit(): void {
    this.loadStoreWithPaging(1, this.limit);
  }

  pageChangedHandler(page: number): void {
    this.loadStoreWithPaging(page, this.limit);
  }

  loadStoreWithPaging(page: number, limit: number): void {
    this.storeSerivce.queryWithPaging(page, limit).subscribe(res => {
      this.pagination = res.body ?? [];
      this.stores = this.pagination?.items ?? [];
    });
  }
}
