import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IVoucherImage, VoucherImage } from '../voucher-image.model';

import { VoucherImageService } from './voucher-image.service';

describe('Service Tests', () => {
  describe('VoucherImage Service', () => {
    let service: VoucherImageService;
    let httpMock: HttpTestingController;
    let elemDefault: IVoucherImage;
    let expectedResult: IVoucherImage | IVoucherImage[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VoucherImageService);
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

      it('should create a VoucherImage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VoucherImage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VoucherImage', () => {
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

      it('should partial update a VoucherImage', () => {
        const patchObject = Object.assign({}, new VoucherImage());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VoucherImage', () => {
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

      it('should delete a VoucherImage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVoucherImageToCollectionIfMissing', () => {
        it('should add a VoucherImage to an empty array', () => {
          const voucherImage: IVoucherImage = { id: 123 };
          expectedResult = service.addVoucherImageToCollectionIfMissing([], voucherImage);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucherImage);
        });

        it('should not add a VoucherImage to an array that contains it', () => {
          const voucherImage: IVoucherImage = { id: 123 };
          const voucherImageCollection: IVoucherImage[] = [
            {
              ...voucherImage,
            },
            { id: 456 },
          ];
          expectedResult = service.addVoucherImageToCollectionIfMissing(voucherImageCollection, voucherImage);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VoucherImage to an array that doesn't contain it", () => {
          const voucherImage: IVoucherImage = { id: 123 };
          const voucherImageCollection: IVoucherImage[] = [{ id: 456 }];
          expectedResult = service.addVoucherImageToCollectionIfMissing(voucherImageCollection, voucherImage);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucherImage);
        });

        it('should add only unique VoucherImage to an array', () => {
          const voucherImageArray: IVoucherImage[] = [{ id: 123 }, { id: 456 }, { id: 79809 }];
          const voucherImageCollection: IVoucherImage[] = [{ id: 123 }];
          expectedResult = service.addVoucherImageToCollectionIfMissing(voucherImageCollection, ...voucherImageArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const voucherImage: IVoucherImage = { id: 123 };
          const voucherImage2: IVoucherImage = { id: 456 };
          expectedResult = service.addVoucherImageToCollectionIfMissing([], voucherImage, voucherImage2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(voucherImage);
          expect(expectedResult).toContain(voucherImage2);
        });

        it('should accept null and undefined values', () => {
          const voucherImage: IVoucherImage = { id: 123 };
          expectedResult = service.addVoucherImageToCollectionIfMissing([], null, voucherImage, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(voucherImage);
        });

        it('should return initial array if no VoucherImage is added', () => {
          const voucherImageCollection: IVoucherImage[] = [{ id: 123 }];
          expectedResult = service.addVoucherImageToCollectionIfMissing(voucherImageCollection, undefined, null);
          expect(expectedResult).toEqual(voucherImageCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
