import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IMyUser, MyUser } from '../my-user.model';

import { MyUserService } from './my-user.service';

describe('Service Tests', () => {
  describe('MyUser Service', () => {
    let service: MyUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IMyUser;
    let expectedResult: IMyUser | IMyUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MyUserService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        username: 'AAAAAAA',
        password: 'AAAAAAA',
        firstName: 'AAAAAAA',
        lastName: 'AAAAAAA',
        gender: 'AAAAAAA',
        phone: 'AAAAAAA',
        email: 'AAAAAAA',
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

      it('should create a MyUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new MyUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MyUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            username: 'BBBBBB',
            password: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            phone: 'BBBBBB',
            email: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a MyUser', () => {
        const patchObject = Object.assign(
          {
            password: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            phone: 'BBBBBB',
          },
          new MyUser()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MyUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            username: 'BBBBBB',
            password: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            gender: 'BBBBBB',
            phone: 'BBBBBB',
            email: 'BBBBBB',
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

      it('should delete a MyUser', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMyUserToCollectionIfMissing', () => {
        it('should add a MyUser to an empty array', () => {
          const myUser: IMyUser = { id: 123 };
          expectedResult = service.addMyUserToCollectionIfMissing([], myUser);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(myUser);
        });

        it('should not add a MyUser to an array that contains it', () => {
          const myUser: IMyUser = { id: 123 };
          const myUserCollection: IMyUser[] = [
            {
              ...myUser,
            },
            { id: 456 },
          ];
          expectedResult = service.addMyUserToCollectionIfMissing(myUserCollection, myUser);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MyUser to an array that doesn't contain it", () => {
          const myUser: IMyUser = { id: 123 };
          const myUserCollection: IMyUser[] = [{ id: 456 }];
          expectedResult = service.addMyUserToCollectionIfMissing(myUserCollection, myUser);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(myUser);
        });

        it('should add only unique MyUser to an array', () => {
          const myUserArray: IMyUser[] = [{ id: 123 }, { id: 456 }, { id: 30476 }];
          const myUserCollection: IMyUser[] = [{ id: 123 }];
          expectedResult = service.addMyUserToCollectionIfMissing(myUserCollection, ...myUserArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const myUser: IMyUser = { id: 123 };
          const myUser2: IMyUser = { id: 456 };
          expectedResult = service.addMyUserToCollectionIfMissing([], myUser, myUser2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(myUser);
          expect(expectedResult).toContain(myUser2);
        });

        it('should accept null and undefined values', () => {
          const myUser: IMyUser = { id: 123 };
          expectedResult = service.addMyUserToCollectionIfMissing([], null, myUser, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(myUser);
        });

        it('should return initial array if no MyUser is added', () => {
          const myUserCollection: IMyUser[] = [{ id: 123 }];
          expectedResult = service.addMyUserToCollectionIfMissing(myUserCollection, undefined, null);
          expect(expectedResult).toEqual(myUserCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
