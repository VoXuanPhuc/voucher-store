import { IVoucher } from 'app/entities/voucher/voucher.model';
import { IStore } from 'app/entities/store/store.model';

export interface IEvent {
  id?: number;
  title?: string;
  description?: string | null;
  vouchers?: IVoucher[] | null;
  store?: IStore | null;
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public vouchers?: IVoucher[] | null,
    public store?: IStore | null
  ) {}
}

export function getEventIdentifier(event: IEvent): number | undefined {
  return event.id;
}
