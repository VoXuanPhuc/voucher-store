import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IBenifitPackage, BenifitPackage } from '../benifit-package.model';

import { BenifitPackageService } from './benifit-package.service';

describe('Service Tests', () => {
  describe('BenifitPackage Service', () => {
    let service: BenifitPackageService;
    let httpMock: HttpTestingController;
    let elemDefault: IBenifitPackage;
    let expectedResult: IBenifitPackage | IBenifitPackage[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(BenifitPackageService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        description: 'AAAAAAA',
        cost: 0,
        time: 'AAAAAAA',
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

      it('should create a BenifitPackage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new BenifitPackage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a BenifitPackage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            cost: 1,
            time: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a BenifitPackage', () => {
        const patchObject = Object.assign(
          {
            time: 'BBBBBB',
          },
          new BenifitPackage()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of BenifitPackage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            description: 'BBBBBB',
            cost: 1,
            time: 'BBBBBB',
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

      it('should delete a BenifitPackage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addBenifitPackageToCollectionIfMissing', () => {
        it('should add a BenifitPackage to an empty array', () => {
          const benifitPackage: IBenifitPackage = { id: 123 };
          expectedResult = service.addBenifitPackageToCollectionIfMissing([], benifitPackage);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(benifitPackage);
        });

        it('should not add a BenifitPackage to an array that contains it', () => {
          const benifitPackage: IBenifitPackage = { id: 123 };
          const benifitPackageCollection: IBenifitPackage[] = [
            {
              ...benifitPackage,
            },
            { id: 456 },
          ];
          expectedResult = service.addBenifitPackageToCollectionIfMissing(benifitPackageCollection, benifitPackage);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a BenifitPackage to an array that doesn't contain it", () => {
          const benifitPackage: IBenifitPackage = { id: 123 };
          const benifitPackageCollection: IBenifitPackage[] = [{ id: 456 }];
          expectedResult = service.addBenifitPackageToCollectionIfMissing(benifitPackageCollection, benifitPackage);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(benifitPackage);
        });

        it('should add only unique BenifitPackage to an array', () => {
          const benifitPackageArray: IBenifitPackage[] = [{ id: 123 }, { id: 456 }, { id: 81905 }];
          const benifitPackageCollection: IBenifitPackage[] = [{ id: 123 }];
          expectedResult = service.addBenifitPackageToCollectionIfMissing(benifitPackageCollection, ...benifitPackageArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const benifitPackage: IBenifitPackage = { id: 123 };
          const benifitPackage2: IBenifitPackage = { id: 456 };
          expectedResult = service.addBenifitPackageToCollectionIfMissing([], benifitPackage, benifitPackage2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(benifitPackage);
          expect(expectedResult).toContain(benifitPackage2);
        });

        it('should accept null and undefined values', () => {
          const benifitPackage: IBenifitPackage = { id: 123 };
          expectedResult = service.addBenifitPackageToCollectionIfMissing([], null, benifitPackage, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(benifitPackage);
        });

        it('should return initial array if no BenifitPackage is added', () => {
          const benifitPackageCollection: IBenifitPackage[] = [{ id: 123 }];
          expectedResult = service.addBenifitPackageToCollectionIfMissing(benifitPackageCollection, undefined, null);
          expect(expectedResult).toEqual(benifitPackageCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
