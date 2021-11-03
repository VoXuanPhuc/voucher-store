import { ICategory } from 'app/entities/category/category.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';

export interface IProduct {
  id?: number;
  code?: string;
  image?: string | null;
  category?: ICategory | null;
  vouchers?: IVoucher[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public code?: string,
    public image?: string | null,
    public category?: ICategory | null,
    public vouchers?: IVoucher[] | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
