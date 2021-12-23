import { IVoucher } from '../voucher/voucher.model';

export interface ICartVoucher {
    total?: number;
    voucher?: IVoucher;
}

export class CartVoucher implements ICartVoucher {
    constructor(public total?: number, public voucher?: IVoucher) {}
}
