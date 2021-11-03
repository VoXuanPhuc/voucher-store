import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVoucherStatus, VoucherStatus } from '../voucher-status.model';

import { VoucherStatusService } from './voucher-status.service';

describe('Service Tests', () => {
  describe('VoucherStatus Service', () => {
    let service: VoucherStatusService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoucherStatus;
    let expectedResult: IVoucherStatus | IVoucherStatus[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VoucherStatusService);
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

      it('should create a VoucherStatus', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VoucherStatus()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VoucherStatus', () => {
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

      it('should partial update a VoucherStatus', () => {
        const patchObject = Object.assign({}, new VoucherStatus());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VoucherStatus', () => {
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

      it('should delete a VoucherStatus', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVoucherStatusToCollectionIfMissing', () => {
        it('should add a VoucherStatus to an empty array', () => {
          const voucherStatus: IVoucherStatus = { id: 123 };
          expectedResult = service.addVoucherStatusToCollectionIfMissing([], voucherStatus);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucherStatus);
        });

        it('should not add a VoucherStatus to an array that contains it', () => {
          const voucherStatus: IVoucherStatus = { id: 123 };
          const voucherStatusCollection: IVoucherStatus[] = [
            {
              ...voucherStatus,
            },
            { id: 456 },
          ];
          expectedResult = service.addVoucherStatusToCollectionIfMissing(voucherStatusCollection, voucherStatus);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VoucherStatus to an array that doesn't contain it", () => {
          const voucherStatus: IVoucherStatus = { id: 123 };
          const voucherStatusCollection: IVoucherStatus[] = [{ id: 456 }];
          expectedResult = service.addVoucherStatusToCollectionIfMissing(voucherStatusCollection, voucherStatus);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucherStatus);
        });

        it('should add only unique VoucherStatus to an array', () => {
          const voucherStatusArray: IVoucherStatus[] = [{ id: 123 }, { id: 456 }, { id: 92752 }];
          const voucherStatusCollection: IVoucherStatus[] = [{ id: 123 }];
          expectedResult = service.addVoucherStatusToCollectionIfMissing(voucherStatusCollection, ...voucherStatusArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const voucherStatus: IVoucherStatus = { id: 123 };
          const voucherStatus2: IVoucherStatus = { id: 456 };
          expectedResult = service.addVoucherStatusToCollectionIfMissing([], voucherStatus, voucherStatus2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucherStatus);
          expect(expectedResult).toContain(voucherStatus2);
        });

        it('should accept null and undefined values', () => {
          const voucherStatus: IVoucherStatus = { id: 123 };
          expectedResult = service.addVoucherStatusToCollectionIfMissing([], null, voucherStatus, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucherStatus);
        });

        it('should return initial array if no VoucherStatus is added', () => {
          const voucherStatusCollection: IVoucherStatus[] = [{ id: 123 }];
          expectedResult = service.addVoucherStatusToCollectionIfMissing(voucherStatusCollection, undefined, null);
          expect(expectedResult).toEqual(voucherStatusCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
