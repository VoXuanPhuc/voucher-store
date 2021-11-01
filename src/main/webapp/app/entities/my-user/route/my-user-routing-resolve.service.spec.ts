jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMyUser, MyUser } from '../my-user.model';
import { MyUserService } from '../service/my-user.service';

import { MyUserRoutingResolveService } from './my-user-routing-resolve.service';

describe('Service Tests', () => {
  describe('MyUser routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: MyUserRoutingResolveService;
    let service: MyUserService;
    let resultMyUser: IMyUser | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(MyUserRoutingResolveService);
      service = TestBed.inject(MyUserService);
      resultMyUser = undefined;
    });

    describe('resolve', () => {
      it('should return IMyUser returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMyUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMyUser).toEqual({ id: 123 });
      });

      it('should return new IMyUser if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMyUser = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultMyUser).toEqual(new MyUser());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MyUser })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultMyUser = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultMyUser).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
