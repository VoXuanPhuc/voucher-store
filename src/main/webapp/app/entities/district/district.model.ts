import { IVillage } from 'app/entities/village/village.model';
import { IProvince } from 'app/entities/province/province.model';

export interface IDistrict {
  id?: number;
  name?: string;
  villages?: IVillage[] | null;
  province?: IProvince | null;
}

export class District implements IDistrict {
  constructor(public id?: number, public name?: string, public villages?: IVillage[] | null, public province?: IProvince | null) {}
}

export function getDistrictIdentifier(district: IDistrict): number | undefined {
  return district.id;
}
