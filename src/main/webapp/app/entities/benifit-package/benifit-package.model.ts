import { IStore } from 'app/entities/store/store.model';

export interface IBenifitPackage {
  id?: number;
  name?: string;
  description?: string | null;
  cost?: number;
  time?: string;
  stores?: IStore[] | null;
}

export class BenifitPackage implements IBenifitPackage {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string | null,
    public cost?: number,
    public time?: string,
    public stores?: IStore[] | null
  ) {}
}

export function getBenifitPackageIdentifier(benifitPackage: IBenifitPackage): number | undefined {
  return benifitPackage.id;
}
