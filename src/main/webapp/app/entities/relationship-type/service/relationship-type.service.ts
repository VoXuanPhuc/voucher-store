import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRelationshipType, getRelationshipTypeIdentifier } from '../relationship-type.model';

export type EntityResponseType = HttpResponse<IRelationshipType>;
export type EntityArrayResponseType = HttpResponse<IRelationshipType[]>;

@Injectable({ providedIn: 'root' })
export class RelationshipTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/relationship-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(relationshipType: IRelationshipType): Observable<EntityResponseType> {
    return this.http.post<IRelationshipType>(this.resourceUrl, relationshipType, { observe: 'response' });
  }

  update(relationshipType: IRelationshipType): Observable<EntityResponseType> {
    return this.http.put<IRelationshipType>(
      `${this.resourceUrl}/${getRelationshipTypeIdentifier(relationshipType) as number}`,
      relationshipType,
      { observe: 'response' }
    );
  }

  partialUpdate(relationshipType: IRelationshipType): Observable<EntityResponseType> {
    return this.http.patch<IRelationshipType>(
      `${this.resourceUrl}/${getRelationshipTypeIdentifier(relationshipType) as number}`,
      relationshipType,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRelationshipType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRelationshipType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRelationshipTypeToCollectionIfMissing(
    relationshipTypeCollection: IRelationshipType[],
    ...relationshipTypesToCheck: (IRelationshipType | null | undefined)[]
  ): IRelationshipType[] {
    const relationshipTypes: IRelationshipType[] = relationshipTypesToCheck.filter(isPresent);
    if (relationshipTypes.length > 0) {
      const relationshipTypeCollectionIdentifiers = relationshipTypeCollection.map(
        relationshipTypeItem => getRelationshipTypeIdentifier(relationshipTypeItem)!
      );
      const relationshipTypesToAdd = relationshipTypes.filter(relationshipTypeItem => {
        const relationshipTypeIdentifier = getRelationshipTypeIdentifier(relationshipTypeItem);
        if (relationshipTypeIdentifier == null || relationshipTypeCollectionIdentifiers.includes(relationshipTypeIdentifier)) {
          return false;
        }
        relationshipTypeCollectionIdentifiers.push(relationshipTypeIdentifier);
        return true;
      });
      return [...relationshipTypesToAdd, ...relationshipTypeCollection];
    }
    return relationshipTypeCollection;
  }
}
