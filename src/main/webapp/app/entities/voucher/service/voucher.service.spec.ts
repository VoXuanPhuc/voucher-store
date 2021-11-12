import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IVoucher, Voucher } from '../voucher.model';

import { VoucherService } from './voucher.service';

describe('Service Tests', () => {
  describe('Voucher Service', () => {
    let service: VoucherService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoucher;
    let expectedResult: IVoucher | IVoucher[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VoucherService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        price: 0,
        quantity: 0,
        startTime: currentDate,
        expriedTime: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startTime: currentDate.format(DATE_TIME_FORMAT),
            expriedTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Voucher', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startTime: currentDate.format(DATE_TIME_FORMAT),
            expriedTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
            expriedTime: currentDate,
          },
          returnedFromService
        );

        service.create(new Voucher()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Voucher', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            price: 1,
            quantity: 1,
            startTime: currentDate.format(DATE_TIME_FORMAT),
            expriedTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
            expriedTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Voucher', () => {
        const patchObject = Object.assign(
          {
            price: 1,
            startTime: currentDate.format(DATE_TIME_FORMAT),
          },
          new Voucher()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            startTime: currentDate,
            expriedTime: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Voucher', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            price: 1,
            quantity: 1,
            startTime: currentDate.format(DATE_TIME_FORMAT),
            expriedTime: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startTime: currentDate,
            expriedTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Voucher', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVoucherToCollectionIfMissing', () => {
        it('should add a Voucher to an empty array', () => {
          const voucher: IVoucher = { id: 123 };
          expectedResult = service.addVoucherToCollectionIfMissing([], voucher);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucher);
        });

        it('should not add a Voucher to an array that contains it', () => {
          const voucher: IVoucher = { id: 123 };
          const voucherCollection: IVoucher[] = [
            {
              ...voucher,
            },
            { id: 456 },
          ];
          expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, voucher);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Voucher to an array that doesn't contain it", () => {
          const voucher: IVoucher = { id: 123 };
          const voucherCollection: IVoucher[] = [{ id: 456 }];
          expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, voucher);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucher);
        });

        it('should add only unique Voucher to an array', () => {
          const voucherArray: IVoucher[] = [{ id: 123 }, { id: 456 }, { id: 57139 }];
          const voucherCollection: IVoucher[] = [{ id: 123 }];
          expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, ...voucherArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const voucher: IVoucher = { id: 123 };
          const voucher2: IVoucher = { id: 456 };
          expectedResult = service.addVoucherToCollectionIfMissing([], voucher, voucher2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucher);
          expect(expectedResult).toContain(voucher2);
        });

        it('should accept null and undefined values', () => {
          const voucher: IVoucher = { id: 123 };
          expectedResult = service.addVoucherToCollectionIfMissing([], null, voucher, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucher);
        });

        it('should return initial array if no Voucher is added', () => {
          const voucherCollection: IVoucher[] = [{ id: 123 }];
          expectedResult = service.addVoucherToCollectionIfMissing(voucherCollection, undefined, null);
          expect(expectedResult).toEqual(voucherCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
