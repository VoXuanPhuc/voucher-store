import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IStoreUser, getStoreUserIdentifier } from '../store-user.model';

export type EntityResponseType = HttpResponse<IStoreUser>;
export type EntityArrayResponseType = HttpResponse<IStoreUser[]>;

@Injectable({ providedIn: 'root' })
export class StoreUserService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/store-users');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(storeUser: IStoreUser): Observable<EntityResponseType> {
    return this.http.post<IStoreUser>(this.resourceUrl, storeUser, { observe: 'response' });
  }

  update(storeUser: IStoreUser): Observable<EntityResponseType> {
    return this.http.put<IStoreUser>(`${this.resourceUrl}/${getStoreUserIdentifier(storeUser) as number}`, storeUser, {
      observe: 'response',
    });
  }

  partialUpdate(storeUser: IStoreUser): Observable<EntityResponseType> {
    return this.http.patch<IStoreUser>(`${this.resourceUrl}/${getStoreUserIdentifier(storeUser) as number}`, storeUser, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IStoreUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IStoreUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addStoreUserToCollectionIfMissing(
    storeUserCollection: IStoreUser[],
    ...storeUsersToCheck: (IStoreUser | null | undefined)[]
  ): IStoreUser[] {
    const storeUsers: IStoreUser[] = storeUsersToCheck.filter(isPresent);
    if (storeUsers.length > 0) {
      const storeUserCollectionIdentifiers = storeUserCollection.map(storeUserItem => getStoreUserIdentifier(storeUserItem)!);
      const storeUsersToAdd = storeUsers.filter(storeUserItem => {
        const storeUserIdentifier = getStoreUserIdentifier(storeUserItem);
        if (storeUserIdentifier == null || storeUserCollectionIdentifiers.includes(storeUserIdentifier)) {
          return false;
        }
        storeUserCollectionIdentifiers.push(storeUserIdentifier);
        return true;
      });
      return [...storeUsersToAdd, ...storeUserCollection];
    }
    return storeUserCollection;
  }
}
