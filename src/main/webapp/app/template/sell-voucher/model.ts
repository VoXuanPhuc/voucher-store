export interface IPriceRange {
  minPrice?: number;
  maxPrice?: number;
}

export class PriceRange implements IPriceRange {
  constructor(public minPrice?: number, public maxPrice?: number) {}

  setMinPrice(min: number): void {
    this.minPrice = min;
  }
  setMaxPrice(max: number): void {
    this.maxPrice = max;
  }
}
