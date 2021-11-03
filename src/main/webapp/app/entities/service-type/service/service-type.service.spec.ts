import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IServiceType, ServiceType } from '../service-type.model';

import { ServiceTypeService } from './service-type.service';

describe('Service Tests', () => {
  describe('ServiceType Service', () => {
    let service: ServiceTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IServiceType;
    let expectedResult: IServiceType | IServiceType[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ServiceTypeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ServiceType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ServiceType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ServiceType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ServiceType', () => {
        const patchObject = Object.assign({}, new ServiceType());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ServiceType', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ServiceType', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addServiceTypeToCollectionIfMissing', () => {
        it('should add a ServiceType to an empty array', () => {
          const serviceType: IServiceType = { id: 123 };
          expectedResult = service.addServiceTypeToCollectionIfMissing([], serviceType);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(serviceType);
        });

        it('should not add a ServiceType to an array that contains it', () => {
          const serviceType: IServiceType = { id: 123 };
          const serviceTypeCollection: IServiceType[] = [
            {
              ...serviceType,
            },
            { id: 456 },
          ];
          expectedResult = service.addServiceTypeToCollectionIfMissing(serviceTypeCollection, serviceType);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ServiceType to an array that doesn't contain it", () => {
          const serviceType: IServiceType = { id: 123 };
          const serviceTypeCollection: IServiceType[] = [{ id: 456 }];
          expectedResult = service.addServiceTypeToCollectionIfMissing(serviceTypeCollection, serviceType);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(serviceType);
        });

        it('should add only unique ServiceType to an array', () => {
          const serviceTypeArray: IServiceType[] = [{ id: 123 }, { id: 456 }, { id: 2746 }];
          const serviceTypeCollection: IServiceType[] = [{ id: 123 }];
          expectedResult = service.addServiceTypeToCollectionIfMissing(serviceTypeCollection, ...serviceTypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const serviceType: IServiceType = { id: 123 };
          const serviceType2: IServiceType = { id: 456 };
          expectedResult = service.addServiceTypeToCollectionIfMissing([], serviceType, serviceType2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(serviceType);
          expect(expectedResult).toContain(serviceType2);
        });

        it('should accept null and undefined values', () => {
          const serviceType: IServiceType = { id: 123 };
          expectedResult = service.addServiceTypeToCollectionIfMissing([], null, serviceType, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(serviceType);
        });

        it('should return initial array if no ServiceType is added', () => {
          const serviceTypeCollection: IServiceType[] = [{ id: 123 }];
          expectedResult = service.addServiceTypeToCollectionIfMissing(serviceTypeCollection, undefined, null);
          expect(expectedResult).toEqual(serviceTypeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
