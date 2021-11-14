import * as dayjs from 'dayjs';
import { IVoucherImage } from 'app/entities/voucher-image/voucher-image.model';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { IProduct } from 'app/entities/product/product.model';
import { IEvent } from 'app/entities/event/event.model';
import { IServiceType } from 'app/entities/service-type/service-type.model';

export interface IVoucher {
  id?: number;
  name?: string;
  price?: number;
  quantity?: number;
  startTime?: dayjs.Dayjs;
  expriedTime?: dayjs.Dayjs;
  voucherImages?: IVoucherImage[] | null;
  voucherCodes?: IVoucherCode[] | null;
  feedbacks?: IFeedback[] | null;
  products?: IProduct[] | null;
  event?: IEvent | null;
  type?: IServiceType | null;
}

export class Voucher implements IVoucher {
  constructor(
    public id?: number,
    public name?: string,
    public price?: number,
    public quantity?: number,
    public startTime?: dayjs.Dayjs,
    public expriedTime?: dayjs.Dayjs,
    public voucherImages?: IVoucherImage[] | null,
    public voucherCodes?: IVoucherCode[] | null,
    public feedbacks?: IFeedback[] | null,
    public products?: IProduct[] | null,
    public event?: IEvent | null,
    public type?: IServiceType | null
  ) {}
}

export function getVoucherIdentifier(voucher: IVoucher): number | undefined {
  return voucher.id;
}
