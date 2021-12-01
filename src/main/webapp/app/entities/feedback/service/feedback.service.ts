import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeedback, getFeedbackIdentifier } from '../feedback.model';

export type EntityResponseType = HttpResponse<IFeedback>;
export type EntityArrayResponseType = HttpResponse<IFeedback[]>;

@Injectable({ providedIn: 'root' })
export class FeedbackService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/feedbacks');
  protected resourceFeedBackByVoucher = this.applicationConfigService.getEndpointFor('api/feedbacks-voucher');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(feedback: IFeedback): Observable<EntityResponseType> {
    return this.http.post<IFeedback>(this.resourceUrl, feedback, { observe: 'response' });
  }

  update(feedback: IFeedback): Observable<EntityResponseType> {
    return this.http.put<IFeedback>(`${this.resourceUrl}/${getFeedbackIdentifier(feedback) as number}`, feedback, { observe: 'response' });
  }

  partialUpdate(feedback: IFeedback): Observable<EntityResponseType> {
    return this.http.patch<IFeedback>(`${this.resourceUrl}/${getFeedbackIdentifier(feedback) as number}`, feedback, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeedback>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeedback[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFeedbackToCollectionIfMissing(feedbackCollection: IFeedback[], ...feedbacksToCheck: (IFeedback | null | undefined)[]): IFeedback[] {
    const feedbacks: IFeedback[] = feedbacksToCheck.filter(isPresent);
    if (feedbacks.length > 0) {
      const feedbackCollectionIdentifiers = feedbackCollection.map(feedbackItem => getFeedbackIdentifier(feedbackItem)!);
      const feedbacksToAdd = feedbacks.filter(feedbackItem => {
        const feedbackIdentifier = getFeedbackIdentifier(feedbackItem);
        if (feedbackIdentifier == null || feedbackCollectionIdentifiers.includes(feedbackIdentifier)) {
          return false;
        }
        feedbackCollectionIdentifiers.push(feedbackIdentifier);
        return true;
      });
      return [...feedbacksToAdd, ...feedbackCollection];
    }
    return feedbackCollection;
  }

  getFeedbacksByVoucher(idVoucher: number, page: number): Observable<EntityArrayResponseType> {
    return this.http.get<IFeedback[]>(`${this.resourceFeedBackByVoucher}/${idVoucher}?page=${page}`, { observe: 'response' });
  }

  countVoucherCode(id: number): Observable<EntityResponseType> {
    return this.http.get(`${this.resourceUrl}/count-feedback-by-voucher/${id}`, { observe: 'response' });
  }

  getFeedbacksByVoucherAndRate(rate: number): Observable<EntityArrayResponseType> {
    return this.http.get<IFeedback[]>(`${this.resourceUrl}/feedbacks-voucher?rate=${rate}`, { observe: 'response' });
  }
}
