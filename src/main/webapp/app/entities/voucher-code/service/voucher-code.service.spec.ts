import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVoucherCode, VoucherCode } from '../voucher-code.model';

import { VoucherCodeService } from './voucher-code.service';

describe('Service Tests', () => {
  describe('VoucherCode Service', () => {
    let service: VoucherCodeService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoucherCode;
    let expectedResult: IVoucherCode | IVoucherCode[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VoucherCodeService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
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

      it('should create a VoucherCode', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VoucherCode()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VoucherCode', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VoucherCode', () => {
        const patchObject = Object.assign(
          {
            code: 'BBBBBB',
          },
          new VoucherCode()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VoucherCode', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
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

      it('should delete a VoucherCode', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVoucherCodeToCollectionIfMissing', () => {
        it('should add a VoucherCode to an empty array', () => {
          const voucherCode: IVoucherCode = { id: 123 };
          expectedResult = service.addVoucherCodeToCollectionIfMissing([], voucherCode);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucherCode);
        });

        it('should not add a VoucherCode to an array that contains it', () => {
          const voucherCode: IVoucherCode = { id: 123 };
          const voucherCodeCollection: IVoucherCode[] = [
            {
              ...voucherCode,
            },
            { id: 456 },
          ];
          expectedResult = service.addVoucherCodeToCollectionIfMissing(voucherCodeCollection, voucherCode);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VoucherCode to an array that doesn't contain it", () => {
          const voucherCode: IVoucherCode = { id: 123 };
          const voucherCodeCollection: IVoucherCode[] = [{ id: 456 }];
          expectedResult = service.addVoucherCodeToCollectionIfMissing(voucherCodeCollection, voucherCode);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucherCode);
        });

        it('should add only unique VoucherCode to an array', () => {
          const voucherCodeArray: IVoucherCode[] = [{ id: 123 }, { id: 456 }, { id: 96682 }];
          const voucherCodeCollection: IVoucherCode[] = [{ id: 123 }];
          expectedResult = service.addVoucherCodeToCollectionIfMissing(voucherCodeCollection, ...voucherCodeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const voucherCode: IVoucherCode = { id: 123 };
          const voucherCode2: IVoucherCode = { id: 456 };
          expectedResult = service.addVoucherCodeToCollectionIfMissing([], voucherCode, voucherCode2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucherCode);
          expect(expectedResult).toContain(voucherCode2);
        });

        it('should accept null and undefined values', () => {
          const voucherCode: IVoucherCode = { id: 123 };
          expectedResult = service.addVoucherCodeToCollectionIfMissing([], null, voucherCode, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucherCode);
        });

        it('should return initial array if no VoucherCode is added', () => {
          const voucherCodeCollection: IVoucherCode[] = [{ id: 123 }];
          expectedResult = service.addVoucherCodeToCollectionIfMissing(voucherCodeCollection, undefined, null);
          expect(expectedResult).toEqual(voucherCodeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
