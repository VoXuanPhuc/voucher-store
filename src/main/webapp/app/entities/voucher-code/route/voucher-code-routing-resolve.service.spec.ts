jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IVoucherCode, VoucherCode } from '../voucher-code.model';
import { VoucherCodeService } from '../service/voucher-code.service';

import { VoucherCodeRoutingResolveService } from './voucher-code-routing-resolve.service';

describe('Service Tests', () => {
  describe('VoucherCode routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: VoucherCodeRoutingResolveService;
    let service: VoucherCodeService;
    let resultVoucherCode: IVoucherCode | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(VoucherCodeRoutingResolveService);
      service = TestBed.inject(VoucherCodeService);
      resultVoucherCode = undefined;
    });

    describe('resolve', () => {
      it('should return IVoucherCode returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucherCode = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVoucherCode).toEqual({ id: 123 });
      });

      it('should return new IVoucherCode if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucherCode = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultVoucherCode).toEqual(new VoucherCode());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as VoucherCode })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultVoucherCode = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultVoucherCode).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
