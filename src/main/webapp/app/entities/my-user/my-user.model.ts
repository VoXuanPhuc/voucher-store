import { IAddress } from 'app/entities/address/address.model';
import { IStoreUser } from 'app/entities/store-user/store-user.model';
import { IOrder } from 'app/entities/order/order.model';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { IGift } from 'app/entities/gift/gift.model';
import { IRole } from 'app/entities/role/role.model';

export interface IMyUser {
  id?: number;
  username?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  gender?: string;
  phone?: string;
  email?: string;
  address?: IAddress | null;
  storeUsers?: IStoreUser[] | null;
  orders?: IOrder[] | null;
  feedbacks?: IFeedback[] | null;
  gifts?: IGift[] | null;
  role?: IRole | null;
}

export class MyUser implements IMyUser {
  constructor(
    public id?: number,
    public username?: string,
    public password?: string,
    public firstName?: string,
    public lastName?: string,
    public gender?: string,
    public phone?: string,
    public email?: string,
    public address?: IAddress | null,
    public storeUsers?: IStoreUser[] | null,
    public orders?: IOrder[] | null,
    public feedbacks?: IFeedback[] | null,
    public gifts?: IGift[] | null,
    public role?: IRole | null
  ) {}
}

export function getMyUserIdentifier(myUser: IMyUser): number | undefined {
  return myUser.id;
}
