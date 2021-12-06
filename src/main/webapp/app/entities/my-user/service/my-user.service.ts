import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMyUser, getMyUserIdentifier } from '../my-user.model';

export type EntityResponseType = HttpResponse<IMyUser>;
export type EntityArrayResponseType = HttpResponse<IMyUser[]>;

@Injectable({ providedIn: 'root' })
export class MyUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/my-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(myUser: IMyUser): Observable<EntityResponseType> {
    return this.http.post<IMyUser>(this.resourceUrl, myUser, { observe: 'response' });
  }

  update(myUser: IMyUser): Observable<EntityResponseType> {
    return this.http.put<IMyUser>(`${this.resourceUrl}/${getMyUserIdentifier(myUser) as number}`, myUser, { observe: 'response' });
  }

  partialUpdate(myUser: IMyUser): Observable<EntityResponseType> {
    return this.http.patch<IMyUser>(`${this.resourceUrl}/${getMyUserIdentifier(myUser) as number}`, myUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMyUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMyUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addMyUserToCollectionIfMissing(myUserCollection: IMyUser[], ...myUsersToCheck: (IMyUser | null | undefined)[]): IMyUser[] {
    const myUsers: IMyUser[] = myUsersToCheck.filter(isPresent);
    if (myUsers.length > 0) {
      const myUserCollectionIdentifiers = myUserCollection.map(myUserItem => getMyUserIdentifier(myUserItem)!);
      const myUsersToAdd = myUsers.filter(myUserItem => {
        const myUserIdentifier = getMyUserIdentifier(myUserItem);
        if (myUserIdentifier == null || myUserCollectionIdentifiers.includes(myUserIdentifier)) {
          return false;
        }
        myUserCollectionIdentifiers.push(myUserIdentifier);
        return true;
      });
      return [...myUsersToAdd, ...myUserCollection];
    }
    return myUserCollection;
  }

  getUserByJWT(): Observable<EntityResponseType> {
    return this.http.get<IMyUser>(`${this.resourceUrl}/get-userByToken`, { observe: 'response' });
  }
}
