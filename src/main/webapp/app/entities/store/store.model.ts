import { IAddress } from 'app/entities/address/address.model';
import { IStoreUser } from 'app/entities/store-user/store-user.model';
import { ICategory } from 'app/entities/category/category.model';
import { IEvent } from 'app/entities/event/event.model';
import { IBenifitPackage } from 'app/entities/benifit-package/benifit-package.model';

export interface IStore {
  id?: number;
  name?: string;
  description?: string | null;
  email?: string;
  phone?: string;
  avartar?: string | null;
  background?: string | null;
  address?: IAddress | null;
  storeUsers?: IStoreUser[] | null;
  categories?: ICategory[] | null;
  events?: IEvent[] | null;
  benifit?: IBenifitPackage | null;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public email?: string,
    public phone?: string,
    public avartar?: string | null,
    public background?: string | null,
    public address?: IAddress | null,
    public storeUsers?: IStoreUser[] | null,
    public categories?: ICategory[] | null,
    public events?: IEvent[] | null,
    public benifit?: IBenifitPackage | null
  ) {}
}

export function getStoreIdentifier(store: IStore): number | undefined {
  return store.id;
}
