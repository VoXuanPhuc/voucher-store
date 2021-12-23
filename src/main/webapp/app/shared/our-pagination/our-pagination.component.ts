import { IOurPagination, OurPagination } from './pagination.model';
import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';

@Component({
  selector: 'jhi-our-pagination',
  templateUrl: './our-pagination.component.html',
  styleUrls: ['./our-pagination.component.scss'],
})
export class OurPaginationComponent implements OnInit {
  @Input() pagination?: IOurPagination;

  @Output() pageChanged: EventEmitter<number> = new EventEmitter();

  constructor() {
    return;
  }

  ngOnInit(): void {
    this.pagination = new OurPagination();

    this.pagination.totalItems = 15;
    this.pagination.itemPerPage = 5;
    this.pagination.currentItemCount = 5;
    this.pagination.totalPages = 3;
    this.pagination.currentPage = 1;

    window.console.log(this.toArray(5));
  }

  toArray(n: number): number[] {
    const arr = [];

    for (let i = 1; i <= n; i++) {
      arr.push(i);
    }

    return arr;
  }

  onPageChange(page: number): void {
    this.pagination?.currentPage ? (this.pagination.currentPage = page) : 0;
    this.pageChanged.emit(page);
  }

  toPreviousPage(): void {
    if (this.pagination?.currentPage) {
      if (this.pagination.currentPage > 1) {
        this.onPageChange(this.pagination.currentPage - 1);
      }
    }
  }

  toNextPage(): void {
    if (this.pagination?.currentPage) {
      if (this.pagination.currentPage < (this.pagination.totalPages ?? 1)) {
        this.onPageChange(this.pagination.currentPage + 1);
      }
    }
  }
}
