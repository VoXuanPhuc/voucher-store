import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IRelationshipType, RelationshipType } from '../relationship-type.model';

import { RelationshipTypeService } from './relationship-type.service';

describe('Service Tests', () => {
  describe('RelationshipType Service', () => {
    let service: RelationshipTypeService;
    let httpMock: HttpTestingController;
    let elemDefault: IRelationshipType;
    let expectedResult: IRelationshipType | IRelationshipType[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(RelationshipTypeService);
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

      it('should create a RelationshipType', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new RelationshipType()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a RelationshipType', () => {
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

      it('should partial update a RelationshipType', () => {
        const patchObject = Object.assign({}, new RelationshipType());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of RelationshipType', () => {
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

      it('should delete a RelationshipType', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addRelationshipTypeToCollectionIfMissing', () => {
        it('should add a RelationshipType to an empty array', () => {
          const relationshipType: IRelationshipType = { id: 123 };
          expectedResult = service.addRelationshipTypeToCollectionIfMissing([], relationshipType);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(relationshipType);
        });

        it('should not add a RelationshipType to an array that contains it', () => {
          const relationshipType: IRelationshipType = { id: 123 };
          const relationshipTypeCollection: IRelationshipType[] = [
            {
              ...relationshipType,
            },
            { id: 456 },
          ];
          expectedResult = service.addRelationshipTypeToCollectionIfMissing(relationshipTypeCollection, relationshipType);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a RelationshipType to an array that doesn't contain it", () => {
          const relationshipType: IRelationshipType = { id: 123 };
          const relationshipTypeCollection: IRelationshipType[] = [{ id: 456 }];
          expectedResult = service.addRelationshipTypeToCollectionIfMissing(relationshipTypeCollection, relationshipType);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(relationshipType);
        });

        it('should add only unique RelationshipType to an array', () => {
          const relationshipTypeArray: IRelationshipType[] = [{ id: 123 }, { id: 456 }, { id: 77360 }];
          const relationshipTypeCollection: IRelationshipType[] = [{ id: 123 }];
          expectedResult = service.addRelationshipTypeToCollectionIfMissing(relationshipTypeCollection, ...relationshipTypeArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const relationshipType: IRelationshipType = { id: 123 };
          const relationshipType2: IRelationshipType = { id: 456 };
          expectedResult = service.addRelationshipTypeToCollectionIfMissing([], relationshipType, relationshipType2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(relationshipType);
          expect(expectedResult).toContain(relationshipType2);
        });

        it('should accept null and undefined values', () => {
          const relationshipType: IRelationshipType = { id: 123 };
          expectedResult = service.addRelationshipTypeToCollectionIfMissing([], null, relationshipType, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(relationshipType);
        });

        it('should return initial array if no RelationshipType is added', () => {
          const relationshipTypeCollection: IRelationshipType[] = [{ id: 123 }];
          expectedResult = service.addRelationshipTypeToCollectionIfMissing(relationshipTypeCollection, undefined, null);
          expect(expectedResult).toEqual(relationshipTypeCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
