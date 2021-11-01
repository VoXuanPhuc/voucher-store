jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IStoreUser, StoreUser } from '../store-user.model';
import { StoreUserService } from '../service/store-user.service';

import { StoreUserRoutingResolveService } from './store-user-routing-resolve.service';

describe('Service Tests', () => {
  describe('StoreUser routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: StoreUserRoutingResolveService;
    let service: StoreUserService;
    let resultStoreUser: IStoreUser | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(StoreUserRoutingResolveService);
      service = TestBed.inject(StoreUserService);
      resultStoreUser = undefined;
    });

    describe('resolve', () => {
      it('should return IStoreUser returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultStoreUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultStoreUser).toEqual({ id: 123 });
      });

      it('should return new IStoreUser if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultStoreUser = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultStoreUser).toEqual(new StoreUser());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as StoreUser })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultStoreUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultStoreUser).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
