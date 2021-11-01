import { IVoucher } from 'app/entities/voucher/voucher.model';

export interface IServiceType {
  id?: number;
  name?: string;
  icon?: string | null;
  vouchers?: IVoucher[] | null;
}

export class ServiceType implements IServiceType {
  constructor(public id?: number, public name?: string, public icon?: string | null, public vouchers?: IVoucher[] | null) {}
}

export function getServiceTypeIdentifier(serviceType: IServiceType): number | undefined {
  return serviceType.id;
}
