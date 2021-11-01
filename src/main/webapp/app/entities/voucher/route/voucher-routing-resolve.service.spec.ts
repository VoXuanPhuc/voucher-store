jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVoucher, Voucher } from '../voucher.model';
import { VoucherService } from '../service/voucher.service';

import { VoucherRoutingResolveService } from './voucher-routing-resolve.service';

describe('Service Tests', () => {
  describe('Voucher routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VoucherRoutingResolveService;
    let service: VoucherService;
    let resultVoucher: IVoucher | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VoucherRoutingResolveService);
      service = TestBed.inject(VoucherService);
      resultVoucher = undefined;
    });

    describe('resolve', () => {
      it('should return IVoucher returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucher = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVoucher).toEqual({ id: 123 });
      });

      it('should return new IVoucher if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucher = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVoucher).toEqual(new Voucher());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as Voucher })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucher = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVoucher).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
