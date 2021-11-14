import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';

export interface IVoucherStatus {
  id?: number;
  name?: string;
  voucherCodes?: IVoucherCode[] | null;
}

export class VoucherStatus implements IVoucherStatus {
  constructor(public id?: number, public name?: string, public voucherCodes?: IVoucherCode[] | null) {}
}

export function getVoucherStatusIdentifier(voucherStatus: IVoucherStatus): number | undefined {
  return voucherStatus.id;
}
