jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVoucherImage, VoucherImage } from '../voucher-image.model';
import { VoucherImageService } from '../service/voucher-image.service';

import { VoucherImageRoutingResolveService } from './voucher-image-routing-resolve.service';

describe('Service Tests', () => {
  describe('VoucherImage routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VoucherImageRoutingResolveService;
    let service: VoucherImageService;
    let resultVoucherImage: IVoucherImage | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VoucherImageRoutingResolveService);
      service = TestBed.inject(VoucherImageService);
      resultVoucherImage = undefined;
    });

    describe('resolve', () => {
      it('should return IVoucherImage returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucherImage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVoucherImage).toEqual({ id: 123 });
      });

      it('should return new IVoucherImage if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucherImage = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVoucherImage).toEqual(new VoucherImage());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VoucherImage })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucherImage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVoucherImage).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
