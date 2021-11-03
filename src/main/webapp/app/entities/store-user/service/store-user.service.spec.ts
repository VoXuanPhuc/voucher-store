import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStoreUser, StoreUser } from '../store-user.model';

import { StoreUserService } from './store-user.service';

describe('Service Tests', () => {
  describe('StoreUser Service', () => {
    let service: StoreUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IStoreUser;
    let expectedResult: IStoreUser | IStoreUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(StoreUserService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
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

      it('should create a StoreUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StoreUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StoreUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a StoreUser', () => {
        const patchObject = Object.assign({}, new StoreUser());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StoreUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
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

      it('should delete a StoreUser', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addStoreUserToCollectionIfMissing', () => {
        it('should add a StoreUser to an empty array', () => {
          const storeUser: IStoreUser = { id: 123 };
          expectedResult = service.addStoreUserToCollectionIfMissing([], storeUser);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(storeUser);
        });

        it('should not add a StoreUser to an array that contains it', () => {
          const storeUser: IStoreUser = { id: 123 };
          const storeUserCollection: IStoreUser[] = [
            {
              ...storeUser,
            },
            { id: 456 },
          ];
          expectedResult = service.addStoreUserToCollectionIfMissing(storeUserCollection, storeUser);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a StoreUser to an array that doesn't contain it", () => {
          const storeUser: IStoreUser = { id: 123 };
          const storeUserCollection: IStoreUser[] = [{ id: 456 }];
          expectedResult = service.addStoreUserToCollectionIfMissing(storeUserCollection, storeUser);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(storeUser);
        });

        it('should add only unique StoreUser to an array', () => {
          const storeUserArray: IStoreUser[] = [{ id: 123 }, { id: 456 }, { id: 92429 }];
          const storeUserCollection: IStoreUser[] = [{ id: 123 }];
          expectedResult = service.addStoreUserToCollectionIfMissing(storeUserCollection, ...storeUserArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const storeUser: IStoreUser = { id: 123 };
          const storeUser2: IStoreUser = { id: 456 };
          expectedResult = service.addStoreUserToCollectionIfMissing([], storeUser, storeUser2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(storeUser);
          expect(expectedResult).toContain(storeUser2);
        });

        it('should accept null and undefined values', () => {
          const storeUser: IStoreUser = { id: 123 };
          expectedResult = service.addStoreUserToCollectionIfMissing([], null, storeUser, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(storeUser);
        });

        it('should return initial array if no StoreUser is added', () => {
          const storeUserCollection: IStoreUser[] = [{ id: 123 }];
          expectedResult = service.addStoreUserToCollectionIfMissing(storeUserCollection, undefined, null);
          expect(expectedResult).toEqual(storeUserCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
