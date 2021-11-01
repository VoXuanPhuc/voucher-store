import { IMyUser } from 'app/entities/my-user/my-user.model';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';

export interface IGift {
  id?: number;
  message?: string | null;
  giver?: IMyUser | null;
  voucher?: IVoucherCode | null;
}

export class Gift implements IGift {
  constructor(public id?: number, public message?: string | null, public giver?: IMyUser | null, public voucher?: IVoucherCode | null) {}
}

export function getGiftIdentifier(gift: IGift): number | undefined {
  return gift.id;
}
