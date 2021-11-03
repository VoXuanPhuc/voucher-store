jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IRelationshipType, RelationshipType } from '../relationship-type.model';
import { RelationshipTypeService } from '../service/relationship-type.service';

import { RelationshipTypeRoutingResolveService } from './relationship-type-routing-resolve.service';

describe('Service Tests', () => {
  describe('RelationshipType routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: RelationshipTypeRoutingResolveService;
    let service: RelationshipTypeService;
    let resultRelationshipType: IRelationshipType | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(RelationshipTypeRoutingResolveService);
      service = TestBed.inject(RelationshipTypeService);
      resultRelationshipType = undefined;
    });

    describe('resolve', () => {
      it('should return IRelationshipType returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRelationshipType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRelationshipType).toEqual({ id: 123 });
      });

      it('should return new IRelationshipType if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRelationshipType = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultRelationshipType).toEqual(new RelationshipType());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as RelationshipType })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultRelationshipType = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultRelationshipType).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
