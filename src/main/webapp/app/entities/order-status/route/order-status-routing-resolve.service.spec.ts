jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IOrderStatus, OrderStatus } from '../order-status.model';
import { OrderStatusService } from '../service/order-status.service';

import { OrderStatusRoutingResolveService } from './order-status-routing-resolve.service';

describe('Service Tests', () => {
  describe('OrderStatus routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: OrderStatusRoutingResolveService;
    let service: OrderStatusService;
    let resultOrderStatus: IOrderStatus | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(OrderStatusRoutingResolveService);
      service = TestBed.inject(OrderStatusService);
      resultOrderStatus = undefined;
    });

    describe('resolve', () => {
      it('should return IOrderStatus returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrderStatus = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrderStatus).toEqual({ id: 123 });
      });

      it('should return new IOrderStatus if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrderStatus = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultOrderStatus).toEqual(new OrderStatus());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as OrderStatus })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultOrderStatus = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultOrderStatus).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
