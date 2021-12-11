import { IMyFilter } from './../../../template/sell-voucher/model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucher, getVoucherIdentifier } from '../voucher.model';

export type EntityResponseType = HttpResponse<IVoucher>;
export type EntityArrayResponseType = HttpResponse<IVoucher[]>;

@Injectable({ providedIn: 'root' })
export class VoucherService {
    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/vouchers');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

    create(voucher: IVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voucher);
        return this.http
            .post<IVoucher>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(voucher: IVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voucher);
        return this.http
            .put<IVoucher>(`${this.resourceUrl}/${getVoucherIdentifier(voucher) as number}`, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    partialUpdate(voucher: IVoucher): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(voucher);
        return this.http
            .patch<IVoucher>(`${this.resourceUrl}/${getVoucherIdentifier(voucher) as number}`, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IVoucher>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findByTypeId(id: number): Observable<EntityArrayResponseType> {
        let params = new HttpParams();
        params = params.set('type', id);
        return this.http
            .get<IVoucher[]>(this.resourceUrl, { params, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IVoucher[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    findWithPaging(myFilter: IMyFilter): Observable<any> {
        let params = new HttpParams();
        params = params.set('page', myFilter.page ?? 1);
        params = params.set('limit', myFilter.limit ?? 6);
        if (myFilter.type) {
            params = params.set('type', myFilter.type);
        }
        if (myFilter.sort) {
            params = params.set('sort', myFilter.sort);
        }

        window.console.log('my filterrrrrrrrrrrr duong: ', myFilter);

        return this.http.get<IVoucher[]>(this.resourceUrl, { params, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    addVoucherToCollectionIfMissing(voucherCollection: IVoucher[], ...vouchersToCheck: (IVoucher | null | undefined)[]): IVoucher[] {
        const vouchers: IVoucher[] = vouchersToCheck.filter(isPresent);
        if (vouchers.length > 0) {
            const voucherCollectionIdentifiers = voucherCollection.map(voucherItem => getVoucherIdentifier(voucherItem)!);
            const vouchersToAdd = vouchers.filter(voucherItem => {
                const voucherIdentifier = getVoucherIdentifier(voucherItem);
                if (voucherIdentifier == null || voucherCollectionIdentifiers.includes(voucherIdentifier)) {
                    return false;
                }
                voucherCollectionIdentifiers.push(voucherIdentifier);
                return true;
            });
            return [...vouchersToAdd, ...voucherCollection];
        }
        return voucherCollection;
    }

    protected convertDateFromClient(voucher: IVoucher): IVoucher {
        return Object.assign({}, voucher, {
            startTime: voucher.startTime?.isValid() ? voucher.startTime.toJSON() : undefined,
            expriedTime: voucher.expriedTime?.isValid() ? voucher.expriedTime.toJSON() : undefined,
        });
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.startTime = res.body.startTime ? dayjs(res.body.startTime) : undefined;
            res.body.expriedTime = res.body.expriedTime ? dayjs(res.body.expriedTime) : undefined;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((voucher: IVoucher) => {
                voucher.startTime = voucher.startTime ? dayjs(voucher.startTime) : undefined;
                voucher.expriedTime = voucher.expriedTime ? dayjs(voucher.expriedTime) : undefined;
            });
        }
        return res;
    }
}
