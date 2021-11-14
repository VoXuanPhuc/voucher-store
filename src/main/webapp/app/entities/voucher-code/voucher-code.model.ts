import { IGift } from 'app/entities/gift/gift.model';
import { IVoucherStatus } from 'app/entities/voucher-status/voucher-status.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { IMyOrder } from 'app/entities/my-order/my-order.model';

export interface IVoucherCode {
  id?: number;
  code?: string;
  gifts?: IGift[] | null;
  status?: IVoucherStatus | null;
  voucher?: IVoucher | null;
  order?: IMyOrder | null;
}

export class VoucherCode implements IVoucherCode {
  constructor(
    public id?: number,
    public code?: string,
    public gifts?: IGift[] | null,
    public status?: IVoucherStatus | null,
    public voucher?: IVoucher | null,
    public order?: IMyOrder | null
  ) {}
}

export function getVoucherCodeIdentifier(voucherCode: IVoucherCode): number | undefined {
  return voucherCode.id;
}
