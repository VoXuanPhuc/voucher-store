import { IGift } from 'app/entities/gift/gift.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { IOrder } from 'app/entities/order/order.model';

export interface IVoucherCode {
  id?: number;
  code?: string;
  gifts?: IGift[] | null;
  voucher?: IVoucher | null;
  order?: IOrder | null;
}

export class VoucherCode implements IVoucherCode {
  constructor(
    public id?: number,
    public code?: string,
    public gifts?: IGift[] | null,
    public voucher?: IVoucher | null,
    public order?: IOrder | null
  ) {}
}

export function getVoucherCodeIdentifier(voucherCode: IVoucherCode): number | undefined {
  return voucherCode.id;
}
