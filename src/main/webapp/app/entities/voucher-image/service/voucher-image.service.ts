import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IVoucherImage, getVoucherImageIdentifier } from '../voucher-image.model';

export type EntityResponseType = HttpResponse<IVoucherImage>;
export type EntityArrayResponseType = HttpResponse<IVoucherImage[]>;

@Injectable({ providedIn: 'root' })
export class VoucherImageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/voucher-images');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(voucherImage: IVoucherImage): Observable<EntityResponseType> {
    return this.http.post<IVoucherImage>(this.resourceUrl, voucherImage, { observe: 'response' });
  }

  update(voucherImage: IVoucherImage): Observable<EntityResponseType> {
    return this.http.put<IVoucherImage>(`${this.resourceUrl}/${getVoucherImageIdentifier(voucherImage) as number}`, voucherImage, {
      observe: 'response',
    });
  }

  partialUpdate(voucherImage: IVoucherImage): Observable<EntityResponseType> {
    return this.http.patch<IVoucherImage>(`${this.resourceUrl}/${getVoucherImageIdentifier(voucherImage) as number}`, voucherImage, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVoucherImage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVoucherImage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addVoucherImageToCollectionIfMissing(
    voucherImageCollection: IVoucherImage[],
    ...voucherImagesToCheck: (IVoucherImage | null | undefined)[]
  ): IVoucherImage[] {
    const voucherImages: IVoucherImage[] = voucherImagesToCheck.filter(isPresent);
    if (voucherImages.length > 0) {
      const voucherImageCollectionIdentifiers = voucherImageCollection.map(
        voucherImageItem => getVoucherImageIdentifier(voucherImageItem)!
      );
      const voucherImagesToAdd = voucherImages.filter(voucherImageItem => {
        const voucherImageIdentifier = getVoucherImageIdentifier(voucherImageItem);
        if (voucherImageIdentifier == null || voucherImageCollectionIdentifiers.includes(voucherImageIdentifier)) {
          return false;
        }
        voucherImageCollectionIdentifiers.push(voucherImageIdentifier);
        return true;
      });
      return [...voucherImagesToAdd, ...voucherImageCollection];
    }
    return voucherImageCollection;
  }
}
