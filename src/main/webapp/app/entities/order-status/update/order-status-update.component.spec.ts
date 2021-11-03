jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { OrderStatusService } from '../service/order-status.service';
import { IOrderStatus, OrderStatus } from '../order-status.model';

import { OrderStatusUpdateComponent } from './order-status-update.component';

describe('Component Tests', () => {
  describe('OrderStatus Management Update Component', () => {
    let comp: OrderStatusUpdateComponent;
    let fixture: ComponentFixture<OrderStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let orderStatusService: OrderStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrderStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(OrderStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      orderStatusService = TestBed.inject(OrderStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const orderStatus: IOrderStatus = { id: 456 };

        activatedRoute.data = of({ orderStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(orderStatus));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OrderStatus>>();
        const orderStatus = { id: 123 };
        jest.spyOn(orderStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ orderStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: orderStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(orderStatusService.update).toHaveBeenCalledWith(orderStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OrderStatus>>();
        const orderStatus = new OrderStatus();
        jest.spyOn(orderStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ orderStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: orderStatus }));
        saveSubject.complete();

        // THEN
        expect(orderStatusService.create).toHaveBeenCalledWith(orderStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<OrderStatus>>();
        const orderStatus = { id: 123 };
        jest.spyOn(orderStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ orderStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(orderStatusService.update).toHaveBeenCalledWith(orderStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
