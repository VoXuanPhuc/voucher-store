jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IBenifitPackage, BenifitPackage } from '../benifit-package.model';
import { BenifitPackageService } from '../service/benifit-package.service';

import { BenifitPackageRoutingResolveService } from './benifit-package-routing-resolve.service';

describe('Service Tests', () => {
  describe('BenifitPackage routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: BenifitPackageRoutingResolveService;
    let service: BenifitPackageService;
    let resultBenifitPackage: IBenifitPackage | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(BenifitPackageRoutingResolveService);
      service = TestBed.inject(BenifitPackageService);
      resultBenifitPackage = undefined;
    });

    describe('resolve', () => {
      it('should return IBenifitPackage returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBenifitPackage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBenifitPackage).toEqual({ id: 123 });
      });

      it('should return new IBenifitPackage if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBenifitPackage = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultBenifitPackage).toEqual(new BenifitPackage());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as BenifitPackage })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultBenifitPackage = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultBenifitPackage).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
