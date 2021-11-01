jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OrderService } from '../service/order.service';
import { IOrder, Order } from '../order.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IOrderStatus } from 'app/entities/order-status/order-status.model';
import { OrderStatusService } from 'app/entities/order-status/service/order-status.service';

import { OrderUpdateComponent } from './order-update.component';

describe('Component Tests', () => {
  describe('Order Management Update Component', () => {
    let comp: OrderUpdateComponent;
    let fixture: ComponentFixture<OrderUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let orderService: OrderService;
    let myUserService: MyUserService;
    let orderStatusService: OrderStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrderUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      orderService = TestBed.inject(OrderService);
      myUserService = TestBed.inject(MyUserService);
      orderStatusService = TestBed.inject(OrderStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call MyUser query and add missing value', () => {
        const order: IOrder = { id: 456 };
        const user: IMyUser = { id: 1862 };
        order.user = user;

        const myUserCollection: IMyUser[] = [{ id: 54627 }];
        jest.spyOn(myUserService, 'query').mockReturnValue(of(new HttpResponse({ body: myUserCollection })));
        const additionalMyUsers = [user];
        const expectedCollection: IMyUser[] = [...additionalMyUsers, ...myUserCollection];
        jest.spyOn(myUserService, 'addMyUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ order });
        comp.ngOnInit();

        expect(myUserService.query).toHaveBeenCalled();
        expect(myUserService.addMyUserToCollectionIfMissing).toHaveBeenCalledWith(myUserCollection, ...additionalMyUsers);
        expect(comp.myUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call OrderStatus query and add missing value', () => {
        const order: IOrder = { id: 456 };
        const status: IOrderStatus = { id: 63281 };
        order.status = status;

        const orderStatusCollection: IOrderStatus[] = [{ id: 56097 }];
        jest.spyOn(orderStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: orderStatusCollection })));
        const additionalOrderStatuses = [status];
        const expectedCollection: IOrderStatus[] = [...additionalOrderStatuses, ...orderStatusCollection];
        jest.spyOn(orderStatusService, 'addOrderStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ order });
        comp.ngOnInit();

        expect(orderStatusService.query).toHaveBeenCalled();
        expect(orderStatusService.addOrderStatusToCollectionIfMissing).toHaveBeenCalledWith(
          orderStatusCollection,
          ...additionalOrderStatuses
        );
        expect(comp.orderStatusesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const order: IOrder = { id: 456 };
        const user: IMyUser = { id: 19492 };
        order.user = user;
        const status: IOrderStatus = { id: 67539 };
        order.status = status;

        activatedRoute.data = of({ order });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(order));
        expect(comp.myUsersSharedCollection).toContain(user);
        expect(comp.orderStatusesSharedCollection).toContain(status);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Order>>();
        const order = { id: 123 };
        jest.spyOn(orderService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ order });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: order }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(orderService.update).toHaveBeenCalledWith(order);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Order>>();
        const order = new Order();
        jest.spyOn(orderService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ order });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: order }));
        saveSubject.complete();

        // THEN
        expect(orderService.create).toHaveBeenCalledWith(order);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Order>>();
        const order = { id: 123 };
        jest.spyOn(orderService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ order });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(orderService.update).toHaveBeenCalledWith(order);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMyUserById', () => {
        it('Should return tracked MyUser primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMyUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackOrderStatusById', () => {
        it('Should return tracked OrderStatus primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackOrderStatusById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
