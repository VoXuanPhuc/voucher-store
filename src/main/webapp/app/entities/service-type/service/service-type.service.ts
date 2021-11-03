import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServiceType, getServiceTypeIdentifier } from '../service-type.model';

export type EntityResponseType = HttpResponse<IServiceType>;
export type EntityArrayResponseType = HttpResponse<IServiceType[]>;

@Injectable({ providedIn: 'root' })
export class ServiceTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/service-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(serviceType: IServiceType): Observable<EntityResponseType> {
    return this.http.post<IServiceType>(this.resourceUrl, serviceType, { observe: 'response' });
  }

  update(serviceType: IServiceType): Observable<EntityResponseType> {
    return this.http.put<IServiceType>(`${this.resourceUrl}/${getServiceTypeIdentifier(serviceType) as number}`, serviceType, {
      observe: 'response',
    });
  }

  partialUpdate(serviceType: IServiceType): Observable<EntityResponseType> {
    return this.http.patch<IServiceType>(`${this.resourceUrl}/${getServiceTypeIdentifier(serviceType) as number}`, serviceType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addServiceTypeToCollectionIfMissing(
    serviceTypeCollection: IServiceType[],
    ...serviceTypesToCheck: (IServiceType | null | undefined)[]
  ): IServiceType[] {
    const serviceTypes: IServiceType[] = serviceTypesToCheck.filter(isPresent);
    if (serviceTypes.length > 0) {
      const serviceTypeCollectionIdentifiers = serviceTypeCollection.map(serviceTypeItem => getServiceTypeIdentifier(serviceTypeItem)!);
      const serviceTypesToAdd = serviceTypes.filter(serviceTypeItem => {
        const serviceTypeIdentifier = getServiceTypeIdentifier(serviceTypeItem);
        if (serviceTypeIdentifier == null || serviceTypeCollectionIdentifiers.includes(serviceTypeIdentifier)) {
          return false;
        }
        serviceTypeCollectionIdentifiers.push(serviceTypeIdentifier);
        return true;
      });
      return [...serviceTypesToAdd, ...serviceTypeCollection];
    }
    return serviceTypeCollection;
  }
}
