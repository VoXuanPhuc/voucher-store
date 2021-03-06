jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IDistrict, District } from '../district.model';
import { DistrictService } from '../service/district.service';

import { DistrictRoutingResolveService } from './district-routing-resolve.service';

describe('Service Tests', () => {
  describe('District routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: DistrictRoutingResolveService;
    let service: DistrictService;
    let resultDistrict: IDistrict | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(DistrictRoutingResolveService);
      service = TestBed.inject(DistrictService);
      resultDistrict = undefined;
    });

    describe('resolve', () => {
      it('should return IDistrict returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDistrict = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDistrict).toEqual({ id: 123 });
      });

      it('should return new IDistrict if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDistrict = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultDistrict).toEqual(new District());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as District })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultDistrict = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultDistrict).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
