import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'jhi-price-range',
  templateUrl: './price-range.component.html',
  styleUrls: ['./price-range.component.scss'],
})
export class PriceRangeComponent implements OnInit {
  minPrice?: number;
  maxPrice?: number;

  @Output() priceRangeChanged = new EventEmitter();

  constructor() {
    return;
  }

  ngOnInit(): void {
    return;
  }

  onPriceRangeChanged(event: any): void {
    const data = { minPrice: this.minPrice, maxPrice: this.maxPrice };
    this.priceRangeChanged.emit(data);
  }
}
