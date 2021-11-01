import { IProduct } from 'app/entities/product/product.model';
import { IStore } from 'app/entities/store/store.model';

export interface ICategory {
  id?: number;
  name?: string;
  products?: IProduct[] | null;
  store?: IStore | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public name?: string, public products?: IProduct[] | null, public store?: IStore | null) {}
}

export function getCategoryIdentifier(category: ICategory): number | undefined {
  return category.id;
}
