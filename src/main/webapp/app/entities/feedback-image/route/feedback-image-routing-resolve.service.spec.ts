jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IFeedbackImage, FeedbackImage } from '../feedback-image.model';
import { FeedbackImageService } from '../service/feedback-image.service';

import { FeedbackImageRoutingResolveService } from './feedback-image-routing-resolve.service';

describe('Service Tests', () => {
  describe('FeedbackImage routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: FeedbackImageRoutingResolveService;
    let service: FeedbackImageService;
    let resultFeedbackImage: IFeedbackImage | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(FeedbackImageRoutingResolveService);
      service = TestBed.inject(FeedbackImageService);
      resultFeedbackImage = undefined;
    });

    describe('resolve', () => {
      it('should return IFeedbackImage returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFeedbackImage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFeedbackImage).toEqual({ id: 123 });
      });

      it('should return new IFeedbackImage if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFeedbackImage = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultFeedbackImage).toEqual(new FeedbackImage());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as FeedbackImage })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultFeedbackImage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultFeedbackImage).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
