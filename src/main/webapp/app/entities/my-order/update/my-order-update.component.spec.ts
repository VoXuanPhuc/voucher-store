jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MyOrderService } from '../service/my-order.service';
import { IMyOrder, MyOrder } from '../my-order.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IOrderStatus } from 'app/entities/order-status/order-status.model';
import { OrderStatusService } from 'app/entities/order-status/service/order-status.service';

import { MyOrderUpdateComponent } from './my-order-update.component';

describe('Component Tests', () => {
  describe('MyOrder Management Update Component', () => {
    let comp: MyOrderUpdateComponent;
    let fixture: ComponentFixture<MyOrderUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let myOrderService: MyOrderService;
    let myUserService: MyUserService;
    let orderStatusService: OrderStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MyOrderUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(MyOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyOrderUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      myOrderService = TestBed.inject(MyOrderService);
      myUserService = TestBed.inject(MyUserService);
      orderStatusService = TestBed.inject(OrderStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call MyUser query and add missing value', () => {
        const myOrder: IMyOrder = { id: 456 };
        const user: IMyUser = { id: 31915 };
        myOrder.user = user;

        const myUserCollection: IMyUser[] = [{ id: 13053 }];
        jest.spyOn(myUserService, 'query').mockReturnValue(of(new HttpResponse({ body: myUserCollection })));
        const additionalMyUsers = [user];
        const expectedCollection: IMyUser[] = [...additionalMyUsers, ...myUserCollection];
        jest.spyOn(myUserService, 'addMyUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ myOrder });
        comp.ngOnInit();

        expect(myUserService.query).toHaveBeenCalled();
        expect(myUserService.addMyUserToCollectionIfMissing).toHaveBeenCalledWith(myUserCollection, ...additionalMyUsers);
        expect(comp.myUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call OrderStatus query and add missing value', () => {
        const myOrder: IMyOrder = { id: 456 };
        const status: IOrderStatus = { id: 67139 };
        myOrder.status = status;

        const orderStatusCollection: IOrderStatus[] = [{ id: 98864 }];
        jest.spyOn(orderStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: orderStatusCollection })));
        const additionalOrderStatuses = [status];
        const expectedCollection: IOrderStatus[] = [...additionalOrderStatuses, ...orderStatusCollection];
        jest.spyOn(orderStatusService, 'addOrderStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ myOrder });
        comp.ngOnInit();

        expect(orderStatusService.query).toHaveBeenCalled();
        expect(orderStatusService.addOrderStatusToCollectionIfMissing).toHaveBeenCalledWith(
          orderStatusCollection,
          ...additionalOrderStatuses
        );
        expect(comp.orderStatusesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const myOrder: IMyOrder = { id: 456 };
        const user: IMyUser = { id: 76072 };
        myOrder.user = user;
        const status: IOrderStatus = { id: 74360 };
        myOrder.status = status;

        activatedRoute.data = of({ myOrder });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(myOrder));
        expect(comp.myUsersSharedCollection).toContain(user);
        expect(comp.orderStatusesSharedCollection).toContain(status);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<MyOrder>>();
        const myOrder = { id: 123 };
        jest.spyOn(myOrderService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ myOrder });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: myOrder }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(myOrderService.update).toHaveBeenCalledWith(myOrder);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<MyOrder>>();
        const myOrder = new MyOrder();
        jest.spyOn(myOrderService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ myOrder });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: myOrder }));
        saveSubject.complete();

        // THEN
        expect(myOrderService.create).toHaveBeenCalledWith(myOrder);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<MyOrder>>();
        const myOrder = { id: 123 };
        jest.spyOn(myOrderService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ myOrder });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(myOrderService.update).toHaveBeenCalledWith(myOrder);
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
