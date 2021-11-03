jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMyOrder, MyOrder } from '../my-order.model';
import { MyOrderService } from '../service/my-order.service';

import { MyOrderRoutingResolveService } from './my-order-routing-resolve.service';

describe('Service Tests', () => {
  describe('MyOrder routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MyOrderRoutingResolveService;
    let service: MyOrderService;
    let resultMyOrder: IMyOrder | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MyOrderRoutingResolveService);
      service = TestBed.inject(MyOrderService);
      resultMyOrder = undefined;
    });

    describe('resolve', () => {
      it('should return IMyOrder returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMyOrder = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMyOrder).toEqual({ id: 123 });
      });

      it('should return new IMyOrder if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMyOrder = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMyOrder).toEqual(new MyOrder());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MyOrder })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMyOrder = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMyOrder).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
