import * as dayjs from 'dayjs';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { IOrderStatus } from 'app/entities/order-status/order-status.model';

export interface IMyOrder {
  id?: number;
  totalCost?: number;
  paymentTime?: dayjs.Dayjs;
  voucherCodes?: IVoucherCode[] | null;
  user?: IMyUser | null;
  status?: IOrderStatus | null;
}

export class MyOrder implements IMyOrder {
  constructor(
    public id?: number,
    public totalCost?: number,
    public paymentTime?: dayjs.Dayjs,
    public voucherCodes?: IVoucherCode[] | null,
    public user?: IMyUser | null,
    public status?: IOrderStatus | null
  ) {}
}

export function getMyOrderIdentifier(myOrder: IMyOrder): number | undefined {
  return myOrder.id;
}
