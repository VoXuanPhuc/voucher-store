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

export interface IMyFilter {
    page?: number;
    limit?: number;
    type?: number | null;
    sort?: string | null;
    search?: string | null;
}

export class MyFilter implements IMyFilter {
    constructor(
        public page?: number,
        public limit?: number,
        public type?: number | null,
        public sort?: string | null,
        public search?: string | null
    ) {}
}
