import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrderStatus, getOrderStatusIdentifier } from '../order-status.model';

export type EntityResponseType = HttpResponse<IOrderStatus>;
export type EntityArrayResponseType = HttpResponse<IOrderStatus[]>;

@Injectable({ providedIn: 'root' })
export class OrderStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/order-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(orderStatus: IOrderStatus): Observable<EntityResponseType> {
    return this.http.post<IOrderStatus>(this.resourceUrl, orderStatus, { observe: 'response' });
  }

  update(orderStatus: IOrderStatus): Observable<EntityResponseType> {
    return this.http.put<IOrderStatus>(`${this.resourceUrl}/${getOrderStatusIdentifier(orderStatus) as number}`, orderStatus, {
      observe: 'response',
    });
  }

  partialUpdate(orderStatus: IOrderStatus): Observable<EntityResponseType> {
    return this.http.patch<IOrderStatus>(`${this.resourceUrl}/${getOrderStatusIdentifier(orderStatus) as number}`, orderStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrderStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrderStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addOrderStatusToCollectionIfMissing(
    orderStatusCollection: IOrderStatus[],
    ...orderStatusesToCheck: (IOrderStatus | null | undefined)[]
  ): IOrderStatus[] {
    const orderStatuses: IOrderStatus[] = orderStatusesToCheck.filter(isPresent);
    if (orderStatuses.length > 0) {
      const orderStatusCollectionIdentifiers = orderStatusCollection.map(orderStatusItem => getOrderStatusIdentifier(orderStatusItem)!);
      const orderStatusesToAdd = orderStatuses.filter(orderStatusItem => {
        const orderStatusIdentifier = getOrderStatusIdentifier(orderStatusItem);
        if (orderStatusIdentifier == null || orderStatusCollectionIdentifiers.includes(orderStatusIdentifier)) {
          return false;
        }
        orderStatusCollectionIdentifiers.push(orderStatusIdentifier);
        return true;
      });
      return [...orderStatusesToAdd, ...orderStatusCollection];
    }
    return orderStatusCollection;
  }
}
