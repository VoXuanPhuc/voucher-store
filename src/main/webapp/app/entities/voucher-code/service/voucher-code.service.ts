import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucherCode, getVoucherCodeIdentifier } from '../voucher-code.model';
import { IOurPagination } from 'app/shared/our-pagination/pagination.model';

export type EntityResponseType = HttpResponse<IVoucherCode>;
export type EntityArrayResponseType = HttpResponse<IVoucherCode[]>;

@Injectable({ providedIn: 'root' })
export class VoucherCodeService {
    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/voucher-codes');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

    create(voucherCode: IVoucherCode): Observable<EntityResponseType> {
        return this.http.post<IVoucherCode>(this.resourceUrl, voucherCode, { observe: 'response' });
    }

    update(voucherCode: IVoucherCode): Observable<EntityResponseType> {
        return this.http.put<IVoucherCode>(`${this.resourceUrl}/${getVoucherCodeIdentifier(voucherCode) as number}`, voucherCode, {
            observe: 'response',
        });
    }

    partialUpdate(voucherCode: IVoucherCode): Observable<EntityResponseType> {
        return this.http.patch<IVoucherCode>(`${this.resourceUrl}/${getVoucherCodeIdentifier(voucherCode) as number}`, voucherCode, {
            observe: 'response',
        });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVoucherCode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVoucherCode[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    addVoucherCodeToCollectionIfMissing(
        voucherCodeCollection: IVoucherCode[],
        ...voucherCodesToCheck: (IVoucherCode | null | undefined)[]
    ): IVoucherCode[] {
        const voucherCodes: IVoucherCode[] = voucherCodesToCheck.filter(isPresent);
        if (voucherCodes.length > 0) {
            const voucherCodeCollectionIdentifiers = voucherCodeCollection.map(
                voucherCodeItem => getVoucherCodeIdentifier(voucherCodeItem)!
            );
            const voucherCodesToAdd = voucherCodes.filter(voucherCodeItem => {
                const voucherCodeIdentifier = getVoucherCodeIdentifier(voucherCodeItem);
                if (voucherCodeIdentifier == null || voucherCodeCollectionIdentifiers.includes(voucherCodeIdentifier)) {
                    return false;
                }
                voucherCodeCollectionIdentifiers.push(voucherCodeIdentifier);
                return true;
            });
            return [...voucherCodesToAdd, ...voucherCodeCollection];
        }
        return voucherCodeCollection;
    }

    countVoucherCode(id: number): Observable<EntityResponseType> {
        return this.http.get(`${this.resourceUrl}/voucher/${id}`, { observe: 'response' });
    }

    queryWithPagingOfUser(page: number, limit: number): Observable<any> {
        let param = new HttpParams();
        param = param.set('page', page);
        param = param.set('limit', limit);

        return this.http.get<IOurPagination>(`${this.resourceUrl}/voucher-codes-of-currentuser`, { params: param, observe: 'response' });
    }

    findByStatusIdAndVoucherIdAndQuantity(statusId: number, voucherId: number, quantity: number): Observable<EntityArrayResponseType> {
        let params = new HttpParams();
        params = params.append('statusId', statusId);
        params = params.append('voucherId', voucherId);
        params = params.append('limit', quantity);

        return this.http.get<IVoucherCode[]>(this.resourceUrl, { params, observe: 'response' });
    }
}
