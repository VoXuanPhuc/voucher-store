import { IStoreUser } from 'app/entities/store-user/store-user.model';

export interface IRelationshipType {
  id?: number;
  name?: string;
  storeUsers?: IStoreUser[] | null;
}

export class RelationshipType implements IRelationshipType {
  constructor(public id?: number, public name?: string, public storeUsers?: IStoreUser[] | null) {}
}

export function getRelationshipTypeIdentifier(relationshipType: IRelationshipType): number | undefined {
  return relationshipType.id;
}
