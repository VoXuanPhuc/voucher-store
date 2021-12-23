import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IMyOrder, getMyOrderIdentifier } from '../my-order.model';

export type EntityResponseType = HttpResponse<IMyOrder>;
export type EntityArrayResponseType = HttpResponse<IMyOrder[]>;

@Injectable({ providedIn: 'root' })
export class MyOrderService {
    protected resourceUrl = this.applicationConfigService.getEndpointFor('api/my-orders');

    constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

    create(myOrder: IMyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(myOrder);
        return this.http
            .post<IMyOrder>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(myOrder: IMyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(myOrder);
        return this.http
            .put<IMyOrder>(`${this.resourceUrl}/${getMyOrderIdentifier(myOrder) as number}`, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    partialUpdate(myOrder: IMyOrder): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(myOrder);
        return this.http
            .patch<IMyOrder>(`${this.resourceUrl}/${getMyOrderIdentifier(myOrder) as number}`, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMyOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMyOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<{}>> {
        return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    addMyOrderToCollectionIfMissing(myOrderCollection: IMyOrder[], ...myOrdersToCheck: (IMyOrder | null | undefined)[]): IMyOrder[] {
        const myOrders: IMyOrder[] = myOrdersToCheck.filter(isPresent);
        if (myOrders.length > 0) {
            const myOrderCollectionIdentifiers = myOrderCollection.map(myOrderItem => getMyOrderIdentifier(myOrderItem)!);
            const myOrdersToAdd = myOrders.filter(myOrderItem => {
                const myOrderIdentifier = getMyOrderIdentifier(myOrderItem);
                if (myOrderIdentifier == null || myOrderCollectionIdentifiers.includes(myOrderIdentifier)) {
                    return false;
                }
                myOrderCollectionIdentifiers.push(myOrderIdentifier);
                return true;
            });
            return [...myOrdersToAdd, ...myOrderCollection];
        }
        return myOrderCollection;
    }

    protected convertDateFromClient(myOrder: IMyOrder): IMyOrder {
        return Object.assign({}, myOrder, {
            paymentTime: myOrder.paymentTime?.isValid() ? myOrder.paymentTime.toJSON() : undefined,
        });
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.paymentTime = res.body.paymentTime ? dayjs(res.body.paymentTime) : undefined;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((myOrder: IMyOrder) => {
                myOrder.paymentTime = myOrder.paymentTime ? dayjs(myOrder.paymentTime) : undefined;
            });
        }
        return res;
    }
}
