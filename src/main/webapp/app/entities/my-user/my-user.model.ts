import { IAddress } from 'app/entities/address/address.model';
import { IStoreUser } from 'app/entities/store-user/store-user.model';
import { IMyOrder } from 'app/entities/my-order/my-order.model';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { IGift } from 'app/entities/gift/gift.model';
import { IRole } from 'app/entities/role/role.model';
import * as dayjs from 'dayjs';

export interface IMyUser {
  id?: number;
  username?: string;
  password?: string;
  firstName?: string;
  lastName?: string;
  gender?: string;
  phone?: string;
  email?: string;
  avatar?: string;
  dob?: dayjs.Dayjs;
  address?: IAddress | null;
  storeUsers?: IStoreUser[] | null;
  myOrders?: IMyOrder[] | null;
  feedbacks?: IFeedback[] | null;
  gifts?: IGift[] | null;
  roles?: IRole[] | null;
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
    public avatar?: string,
    public dob?: dayjs.Dayjs,
    public address?: IAddress | null,
    public storeUsers?: IStoreUser[] | null,
    public myOrders?: IMyOrder[] | null,
    public feedbacks?: IFeedback[] | null,
    public gifts?: IGift[] | null,
    public roles?: IRole[] | null
  ) {}
}

export function getMyUserIdentifier(myUser: IMyUser): number | undefined {
  return myUser.id;
}
