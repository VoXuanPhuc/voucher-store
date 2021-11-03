import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IFeedbackImage, FeedbackImage } from '../feedback-image.model';

import { FeedbackImageService } from './feedback-image.service';

describe('Service Tests', () => {
  describe('FeedbackImage Service', () => {
    let service: FeedbackImageService;
    let httpMock: HttpTestingController;
    let elemDefault: IFeedbackImage;
    let expectedResult: IFeedbackImage | IFeedbackImage[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FeedbackImageService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        content: 'AAAAAAA',
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

      it('should create a FeedbackImage', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new FeedbackImage()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a FeedbackImage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            content: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a FeedbackImage', () => {
        const patchObject = Object.assign({}, new FeedbackImage());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of FeedbackImage', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            content: 'BBBBBB',
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

      it('should delete a FeedbackImage', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFeedbackImageToCollectionIfMissing', () => {
        it('should add a FeedbackImage to an empty array', () => {
          const feedbackImage: IFeedbackImage = { id: 123 };
          expectedResult = service.addFeedbackImageToCollectionIfMissing([], feedbackImage);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(feedbackImage);
        });

        it('should not add a FeedbackImage to an array that contains it', () => {
          const feedbackImage: IFeedbackImage = { id: 123 };
          const feedbackImageCollection: IFeedbackImage[] = [
            {
              ...feedbackImage,
            },
            { id: 456 },
          ];
          expectedResult = service.addFeedbackImageToCollectionIfMissing(feedbackImageCollection, feedbackImage);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a FeedbackImage to an array that doesn't contain it", () => {
          const feedbackImage: IFeedbackImage = { id: 123 };
          const feedbackImageCollection: IFeedbackImage[] = [{ id: 456 }];
          expectedResult = service.addFeedbackImageToCollectionIfMissing(feedbackImageCollection, feedbackImage);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(feedbackImage);
        });

        it('should add only unique FeedbackImage to an array', () => {
          const feedbackImageArray: IFeedbackImage[] = [{ id: 123 }, { id: 456 }, { id: 61078 }];
          const feedbackImageCollection: IFeedbackImage[] = [{ id: 123 }];
          expectedResult = service.addFeedbackImageToCollectionIfMissing(feedbackImageCollection, ...feedbackImageArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const feedbackImage: IFeedbackImage = { id: 123 };
          const feedbackImage2: IFeedbackImage = { id: 456 };
          expectedResult = service.addFeedbackImageToCollectionIfMissing([], feedbackImage, feedbackImage2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(feedbackImage);
          expect(expectedResult).toContain(feedbackImage2);
        });

        it('should accept null and undefined values', () => {
          const feedbackImage: IFeedbackImage = { id: 123 };
          expectedResult = service.addFeedbackImageToCollectionIfMissing([], null, feedbackImage, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(feedbackImage);
        });

        it('should return initial array if no FeedbackImage is added', () => {
          const feedbackImageCollection: IFeedbackImage[] = [{ id: 123 }];
          expectedResult = service.addFeedbackImageToCollectionIfMissing(feedbackImageCollection, undefined, null);
          expect(expectedResult).toEqual(feedbackImageCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
