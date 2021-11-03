import { IMyOrder } from 'app/entities/my-order/my-order.model';

export interface IOrderStatus {
  id?: number;
  name?: string;
  myOrders?: IMyOrder[] | null;
}

export class OrderStatus implements IOrderStatus {
  constructor(public id?: number, public name?: string, public myOrders?: IMyOrder[] | null) {}
}

export function getOrderStatusIdentifier(orderStatus: IOrderStatus): number | undefined {
  return orderStatus.id;
}
