import { IOrder } from 'app/entities/order/order.model';

export interface IOrderStatus {
  id?: number;
  name?: string;
  orders?: IOrder[] | null;
}

export class OrderStatus implements IOrderStatus {
  constructor(public id?: number, public name?: string, public orders?: IOrder[] | null) {}
}

export function getOrderStatusIdentifier(orderStatus: IOrderStatus): number | undefined {
  return orderStatus.id;
}
