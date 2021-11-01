import { IAddress } from 'app/entities/address/address.model';
import { IDistrict } from 'app/entities/district/district.model';

export interface IVillage {
  id?: number;
  name?: string;
  addresses?: IAddress[] | null;
  district?: IDistrict | null;
}

export class Village implements IVillage {
  constructor(public id?: number, public name?: string, public addresses?: IAddress[] | null, public district?: IDistrict | null) {}
}

export function getVillageIdentifier(village: IVillage): number | undefined {
  return village.id;
}
