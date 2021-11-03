jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IServiceType, ServiceType } from '../service-type.model';
import { ServiceTypeService } from '../service/service-type.service';

import { ServiceTypeRoutingResolveService } from './service-type-routing-resolve.service';

describe('Service Tests', () => {
  describe('ServiceType routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ServiceTypeRoutingResolveService;
    let service: ServiceTypeService;
    let resultServiceType: IServiceType | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ServiceTypeRoutingResolveService);
      service = TestBed.inject(ServiceTypeService);
      resultServiceType = undefined;
    });

    describe('resolve', () => {
      it('should return IServiceType returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServiceType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultServiceType).toEqual({ id: 123 });
      });

      it('should return new IServiceType if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServiceType = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultServiceType).toEqual(new ServiceType());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ServiceType })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultServiceType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultServiceType).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
