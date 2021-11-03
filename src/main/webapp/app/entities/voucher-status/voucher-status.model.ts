import { IVoucher } from 'app/entities/voucher/voucher.model';

export interface IVoucherStatus {
  id?: number;
  name?: string;
  vouchers?: IVoucher[] | null;
}

export class VoucherStatus implements IVoucherStatus {
  constructor(public id?: number, public name?: string, public vouchers?: IVoucher[] | null) {}
}

export function getVoucherStatusIdentifier(voucherStatus: IVoucherStatus): number | undefined {
  return voucherStatus.id;
}
