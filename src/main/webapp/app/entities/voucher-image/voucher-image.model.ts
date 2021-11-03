import { IVoucher } from 'app/entities/voucher/voucher.model';

export interface IVoucherImage {
  id?: number;
  name?: string;
  voucher?: IVoucher | null;
}

export class VoucherImage implements IVoucherImage {
  constructor(public id?: number, public name?: string, public voucher?: IVoucher | null) {}
}

export function getVoucherImageIdentifier(voucherImage: IVoucherImage): number | undefined {
  return voucherImage.id;
}
