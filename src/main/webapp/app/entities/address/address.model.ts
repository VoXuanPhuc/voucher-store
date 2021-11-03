import { IVillage } from 'app/entities/village/village.model';

export interface IAddress {
  id?: number;
  street?: string | null;
  zipCode?: number;
  village?: IVillage | null;
}

export class Address implements IAddress {
  constructor(public id?: number, public street?: string | null, public zipCode?: number, public village?: IVillage | null) {}
}

export function getAddressIdentifier(address: IAddress): number | undefined {
  return address.id;
}
