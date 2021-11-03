import { IMyUser } from 'app/entities/my-user/my-user.model';

export interface IRole {
  id?: number;
  name?: string;
  code?: string;
  myUsers?: IMyUser[] | null;
}

export class Role implements IRole {
  constructor(public id?: number, public name?: string, public code?: string, public myUsers?: IMyUser[] | null) {}
}

export function getRoleIdentifier(role: IRole): number | undefined {
  return role.id;
}
