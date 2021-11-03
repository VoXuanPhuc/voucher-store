import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucherStatus, getVoucherStatusIdentifier } from '../voucher-status.model';

export type EntityResponseType = HttpResponse<IVoucherStatus>;
export type EntityArrayResponseType = HttpResponse<IVoucherStatus[]>;

@Injectable({ providedIn: 'root' })
export class VoucherStatusService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/voucher-statuses');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(voucherStatus: IVoucherStatus): Observable<EntityResponseType> {
    return this.http.post<IVoucherStatus>(this.resourceUrl, voucherStatus, { observe: 'response' });
  }

  update(voucherStatus: IVoucherStatus): Observable<EntityResponseType> {
    return this.http.put<IVoucherStatus>(`${this.resourceUrl}/${getVoucherStatusIdentifier(voucherStatus) as number}`, voucherStatus, {
      observe: 'response',
    });
  }

  partialUpdate(voucherStatus: IVoucherStatus): Observable<EntityResponseType> {
    return this.http.patch<IVoucherStatus>(`${this.resourceUrl}/${getVoucherStatusIdentifier(voucherStatus) as number}`, voucherStatus, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoucherStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoucherStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVoucherStatusToCollectionIfMissing(
    voucherStatusCollection: IVoucherStatus[],
    ...voucherStatusesToCheck: (IVoucherStatus | null | undefined)[]
  ): IVoucherStatus[] {
    const voucherStatuses: IVoucherStatus[] = voucherStatusesToCheck.filter(isPresent);
    if (voucherStatuses.length > 0) {
      const voucherStatusCollectionIdentifiers = voucherStatusCollection.map(
        voucherStatusItem => getVoucherStatusIdentifier(voucherStatusItem)!
      );
      const voucherStatusesToAdd = voucherStatuses.filter(voucherStatusItem => {
        const voucherStatusIdentifier = getVoucherStatusIdentifier(voucherStatusItem);
        if (voucherStatusIdentifier == null || voucherStatusCollectionIdentifiers.includes(voucherStatusIdentifier)) {
          return false;
        }
        voucherStatusCollectionIdentifiers.push(voucherStatusIdentifier);
        return true;
      });
      return [...voucherStatusesToAdd, ...voucherStatusCollection];
    }
    return voucherStatusCollection;
  }
}
