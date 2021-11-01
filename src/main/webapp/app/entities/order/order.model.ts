import * as dayjs from 'dayjs';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { IOrderStatus } from 'app/entities/order-status/order-status.model';

export interface IOrder {
  id?: number;
  totalCost?: number;
  paymentTime?: dayjs.Dayjs;
  voucherCodes?: IVoucherCode[] | null;
  user?: IMyUser | null;
  status?: IOrderStatus | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public totalCost?: number,
    public paymentTime?: dayjs.Dayjs,
    public voucherCodes?: IVoucherCode[] | null,
    public user?: IMyUser | null,
    public status?: IOrderStatus | null
  ) {}
}

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}
