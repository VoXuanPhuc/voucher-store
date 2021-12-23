export interface IOurPagination {
  totalItems?: number;
  itemPerPage?: number;
  currentItemCount?: number;

  totalPages?: number;
  currentPage?: number;

  items?: any[] | null;
}

export class OurPagination implements IOurPagination {
  constructor(
    public totalItems?: number,
    public itemPerPage?: number,
    public currentItemCount?: number,

    public totalPages?: number,
    public currentPage?: number,

    public items?: any[] | null
  ) {}
}
