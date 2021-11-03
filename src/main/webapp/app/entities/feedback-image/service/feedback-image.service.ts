import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFeedbackImage, getFeedbackImageIdentifier } from '../feedback-image.model';

export type EntityResponseType = HttpResponse<IFeedbackImage>;
export type EntityArrayResponseType = HttpResponse<IFeedbackImage[]>;

@Injectable({ providedIn: 'root' })
export class FeedbackImageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/feedback-images');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(feedbackImage: IFeedbackImage): Observable<EntityResponseType> {
    return this.http.post<IFeedbackImage>(this.resourceUrl, feedbackImage, { observe: 'response' });
  }

  update(feedbackImage: IFeedbackImage): Observable<EntityResponseType> {
    return this.http.put<IFeedbackImage>(`${this.resourceUrl}/${getFeedbackImageIdentifier(feedbackImage) as number}`, feedbackImage, {
      observe: 'response',
    });
  }

  partialUpdate(feedbackImage: IFeedbackImage): Observable<EntityResponseType> {
    return this.http.patch<IFeedbackImage>(`${this.resourceUrl}/${getFeedbackImageIdentifier(feedbackImage) as number}`, feedbackImage, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFeedbackImage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFeedbackImage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFeedbackImageToCollectionIfMissing(
    feedbackImageCollection: IFeedbackImage[],
    ...feedbackImagesToCheck: (IFeedbackImage | null | undefined)[]
  ): IFeedbackImage[] {
    const feedbackImages: IFeedbackImage[] = feedbackImagesToCheck.filter(isPresent);
    if (feedbackImages.length > 0) {
      const feedbackImageCollectionIdentifiers = feedbackImageCollection.map(
        feedbackImageItem => getFeedbackImageIdentifier(feedbackImageItem)!
      );
      const feedbackImagesToAdd = feedbackImages.filter(feedbackImageItem => {
        const feedbackImageIdentifier = getFeedbackImageIdentifier(feedbackImageItem);
        if (feedbackImageIdentifier == null || feedbackImageCollectionIdentifiers.includes(feedbackImageIdentifier)) {
          return false;
        }
        feedbackImageCollectionIdentifiers.push(feedbackImageIdentifier);
        return true;
      });
      return [...feedbackImagesToAdd, ...feedbackImageCollection];
    }
    return feedbackImageCollection;
  }
}
