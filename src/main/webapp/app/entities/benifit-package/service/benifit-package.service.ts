import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBenifitPackage, getBenifitPackageIdentifier } from '../benifit-package.model';

export type EntityResponseType = HttpResponse<IBenifitPackage>;
export type EntityArrayResponseType = HttpResponse<IBenifitPackage[]>;

@Injectable({ providedIn: 'root' })
export class BenifitPackageService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/benifit-packages');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(benifitPackage: IBenifitPackage): Observable<EntityResponseType> {
    return this.http.post<IBenifitPackage>(this.resourceUrl, benifitPackage, { observe: 'response' });
  }

  update(benifitPackage: IBenifitPackage): Observable<EntityResponseType> {
    return this.http.put<IBenifitPackage>(`${this.resourceUrl}/${getBenifitPackageIdentifier(benifitPackage) as number}`, benifitPackage, {
      observe: 'response',
    });
  }

  partialUpdate(benifitPackage: IBenifitPackage): Observable<EntityResponseType> {
    return this.http.patch<IBenifitPackage>(
      `${this.resourceUrl}/${getBenifitPackageIdentifier(benifitPackage) as number}`,
      benifitPackage,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBenifitPackage>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBenifitPackage[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addBenifitPackageToCollectionIfMissing(
    benifitPackageCollection: IBenifitPackage[],
    ...benifitPackagesToCheck: (IBenifitPackage | null | undefined)[]
  ): IBenifitPackage[] {
    const benifitPackages: IBenifitPackage[] = benifitPackagesToCheck.filter(isPresent);
    if (benifitPackages.length > 0) {
      const benifitPackageCollectionIdentifiers = benifitPackageCollection.map(
        benifitPackageItem => getBenifitPackageIdentifier(benifitPackageItem)!
      );
      const benifitPackagesToAdd = benifitPackages.filter(benifitPackageItem => {
        const benifitPackageIdentifier = getBenifitPackageIdentifier(benifitPackageItem);
        if (benifitPackageIdentifier == null || benifitPackageCollectionIdentifiers.includes(benifitPackageIdentifier)) {
          return false;
        }
        benifitPackageCollectionIdentifiers.push(benifitPackageIdentifier);
        return true;
      });
      return [...benifitPackagesToAdd, ...benifitPackageCollection];
    }
    return benifitPackageCollection;
  }
}
