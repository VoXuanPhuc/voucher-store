import { IDistrict } from 'app/entities/district/district.model';

export interface IProvince {
  id?: number;
  name?: string;
  districts?: IDistrict[] | null;
}

export class Province implements IProvince {
  constructor(public id?: number, public name?: string, public districts?: IDistrict[] | null) {}
}

export function getProvinceIdentifier(province: IProvince): number | undefined {
  return province.id;
}
