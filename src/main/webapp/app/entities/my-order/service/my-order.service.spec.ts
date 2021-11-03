import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IMyOrder, MyOrder } from '../my-order.model';

import { MyOrderService } from './my-order.service';

describe('Service Tests', () => {
  describe('MyOrder Service', () => {
    let service: MyOrderService;
    let httpMock: HttpTestingController;
    let elemDefault: IMyOrder;
    let expectedResult: IMyOrder | IMyOrder[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(MyOrderService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        totalCost: 0,
        paymentTime: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            paymentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a MyOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            paymentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentTime: currentDate,
          },
          returnedFromService
        );

        service.create(new MyOrder()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a MyOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            totalCost: 1,
            paymentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a MyOrder', () => {
        const patchObject = Object.assign(
          {
            paymentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          new MyOrder()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            paymentTime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of MyOrder', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            totalCost: 1,
            paymentTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            paymentTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a MyOrder', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addMyOrderToCollectionIfMissing', () => {
        it('should add a MyOrder to an empty array', () => {
          const myOrder: IMyOrder = { id: 123 };
          expectedResult = service.addMyOrderToCollectionIfMissing([], myOrder);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(myOrder);
        });

        it('should not add a MyOrder to an array that contains it', () => {
          const myOrder: IMyOrder = { id: 123 };
          const myOrderCollection: IMyOrder[] = [
            {
              ...myOrder,
            },
            { id: 456 },
          ];
          expectedResult = service.addMyOrderToCollectionIfMissing(myOrderCollection, myOrder);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a MyOrder to an array that doesn't contain it", () => {
          const myOrder: IMyOrder = { id: 123 };
          const myOrderCollection: IMyOrder[] = [{ id: 456 }];
          expectedResult = service.addMyOrderToCollectionIfMissing(myOrderCollection, myOrder);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(myOrder);
        });

        it('should add only unique MyOrder to an array', () => {
          const myOrderArray: IMyOrder[] = [{ id: 123 }, { id: 456 }, { id: 27254 }];
          const myOrderCollection: IMyOrder[] = [{ id: 123 }];
          expectedResult = service.addMyOrderToCollectionIfMissing(myOrderCollection, ...myOrderArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const myOrder: IMyOrder = { id: 123 };
          const myOrder2: IMyOrder = { id: 456 };
          expectedResult = service.addMyOrderToCollectionIfMissing([], myOrder, myOrder2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(myOrder);
          expect(expectedResult).toContain(myOrder2);
        });

        it('should accept null and undefined values', () => {
          const myOrder: IMyOrder = { id: 123 };
          expectedResult = service.addMyOrderToCollectionIfMissing([], null, myOrder, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(myOrder);
        });

        it('should return initial array if no MyOrder is added', () => {
          const myOrderCollection: IMyOrder[] = [{ id: 123 }];
          expectedResult = service.addMyOrderToCollectionIfMissing(myOrderCollection, undefined, null);
          expect(expectedResult).toEqual(myOrderCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
