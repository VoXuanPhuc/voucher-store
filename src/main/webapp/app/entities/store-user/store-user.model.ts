import { IRelationshipType } from 'app/entities/relationship-type/relationship-type.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { IStore } from 'app/entities/store/store.model';

export interface IStoreUser {
  id?: number;
  type?: IRelationshipType | null;
  user?: IMyUser | null;
  store?: IStore | null;
}

export class StoreUser implements IStoreUser {
  constructor(public id?: number, public type?: IRelationshipType | null, public user?: IMyUser | null, public store?: IStore | null) {}
}

export function getStoreUserIdentifier(storeUser: IStoreUser): number | undefined {
  return storeUser.id;
}
