import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IOrderStatus, OrderStatus } from '../order-status.model';

import { OrderStatusService } from './order-status.service';

describe('Service Tests', () => {
  describe('OrderStatus Service', () => {
    let service: OrderStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IOrderStatus;
    let expectedResult: IOrderStatus | IOrderStatus[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(OrderStatusService);
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

      it('should create a OrderStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new OrderStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OrderStatus', () => {
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

      it('should partial update a OrderStatus', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new OrderStatus()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OrderStatus', () => {
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

      it('should delete a OrderStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addOrderStatusToCollectionIfMissing', () => {
        it('should add a OrderStatus to an empty array', () => {
          const orderStatus: IOrderStatus = { id: 123 };
          expectedResult = service.addOrderStatusToCollectionIfMissing([], orderStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(orderStatus);
        });

        it('should not add a OrderStatus to an array that contains it', () => {
          const orderStatus: IOrderStatus = { id: 123 };
          const orderStatusCollection: IOrderStatus[] = [
            {
              ...orderStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addOrderStatusToCollectionIfMissing(orderStatusCollection, orderStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a OrderStatus to an array that doesn't contain it", () => {
          const orderStatus: IOrderStatus = { id: 123 };
          const orderStatusCollection: IOrderStatus[] = [{ id: 456 }];
          expectedResult = service.addOrderStatusToCollectionIfMissing(orderStatusCollection, orderStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(orderStatus);
        });

        it('should add only unique OrderStatus to an array', () => {
          const orderStatusArray: IOrderStatus[] = [{ id: 123 }, { id: 456 }, { id: 82440 }];
          const orderStatusCollection: IOrderStatus[] = [{ id: 123 }];
          expectedResult = service.addOrderStatusToCollectionIfMissing(orderStatusCollection, ...orderStatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const orderStatus: IOrderStatus = { id: 123 };
          const orderStatus2: IOrderStatus = { id: 456 };
          expectedResult = service.addOrderStatusToCollectionIfMissing([], orderStatus, orderStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(orderStatus);
          expect(expectedResult).toContain(orderStatus2);
        });

        it('should accept null and undefined values', () => {
          const orderStatus: IOrderStatus = { id: 123 };
          expectedResult = service.addOrderStatusToCollectionIfMissing([], null, orderStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(orderStatus);
        });

        it('should return initial array if no OrderStatus is added', () => {
          const orderStatusCollection: IOrderStatus[] = [{ id: 123 }];
          expectedResult = service.addOrderStatusToCollectionIfMissing(orderStatusCollection, undefined, null);
          expect(expectedResult).toEqual(orderStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
