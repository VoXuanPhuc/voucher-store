jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VoucherService } from '../service/voucher.service';
import { IVoucher, Voucher } from '../voucher.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IEvent } from 'app/entities/event/event.model';
import { EventService } from 'app/entities/event/service/event.service';
import { IServiceType } from 'app/entities/service-type/service-type.model';
import { ServiceTypeService } from 'app/entities/service-type/service/service-type.service';

import { VoucherUpdateComponent } from './voucher-update.component';

describe('Component Tests', () => {
  describe('Voucher Management Update Component', () => {
    let comp: VoucherUpdateComponent;
    let fixture: ComponentFixture<VoucherUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let voucherService: VoucherService;
    let productService: ProductService;
    let eventService: EventService;
    let serviceTypeService: ServiceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VoucherUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      voucherService = TestBed.inject(VoucherService);
      productService = TestBed.inject(ProductService);
      eventService = TestBed.inject(EventService);
      serviceTypeService = TestBed.inject(ServiceTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Product query and add missing value', () => {
        const voucher: IVoucher = { id: 456 };
        const products: IProduct[] = [{ id: 14171 }];
        voucher.products = products;

        const productCollection: IProduct[] = [{ id: 99338 }];
        jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
        const additionalProducts = [...products];
        const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
        jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        expect(productService.query).toHaveBeenCalled();
        expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
        expect(comp.productsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Event query and add missing value', () => {
        const voucher: IVoucher = { id: 456 };
        const event: IEvent = { id: 45655 };
        voucher.event = event;

        const eventCollection: IEvent[] = [{ id: 72283 }];
        jest.spyOn(eventService, 'query').mockReturnValue(of(new HttpResponse({ body: eventCollection })));
        const additionalEvents = [event];
        const expectedCollection: IEvent[] = [...additionalEvents, ...eventCollection];
        jest.spyOn(eventService, 'addEventToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        expect(eventService.query).toHaveBeenCalled();
        expect(eventService.addEventToCollectionIfMissing).toHaveBeenCalledWith(eventCollection, ...additionalEvents);
        expect(comp.eventsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call ServiceType query and add missing value', () => {
        const voucher: IVoucher = { id: 456 };
        const type: IServiceType = { id: 92423 };
        voucher.type = type;

        const serviceTypeCollection: IServiceType[] = [{ id: 38229 }];
        jest.spyOn(serviceTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: serviceTypeCollection })));
        const additionalServiceTypes = [type];
        const expectedCollection: IServiceType[] = [...additionalServiceTypes, ...serviceTypeCollection];
        jest.spyOn(serviceTypeService, 'addServiceTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        expect(serviceTypeService.query).toHaveBeenCalled();
        expect(serviceTypeService.addServiceTypeToCollectionIfMissing).toHaveBeenCalledWith(
          serviceTypeCollection,
          ...additionalServiceTypes
        );
        expect(comp.serviceTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const voucher: IVoucher = { id: 456 };
        const products: IProduct = { id: 65524 };
        voucher.products = [products];
        const event: IEvent = { id: 93850 };
        voucher.event = event;
        const type: IServiceType = { id: 19020 };
        voucher.type = type;

        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(voucher));
        expect(comp.productsSharedCollection).toContain(products);
        expect(comp.eventsSharedCollection).toContain(event);
        expect(comp.serviceTypesSharedCollection).toContain(type);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Voucher>>();
        const voucher = { id: 123 };
        jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucher }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(voucherService.update).toHaveBeenCalledWith(voucher);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Voucher>>();
        const voucher = new Voucher();
        jest.spyOn(voucherService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucher }));
        saveSubject.complete();

        // THEN
        expect(voucherService.create).toHaveBeenCalledWith(voucher);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Voucher>>();
        const voucher = { id: 123 };
        jest.spyOn(voucherService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucher });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(voucherService.update).toHaveBeenCalledWith(voucher);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackProductById', () => {
        it('Should return tracked Product primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackProductById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackEventById', () => {
        it('Should return tracked Event primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackEventById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackServiceTypeById', () => {
        it('Should return tracked ServiceType primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackServiceTypeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedProduct', () => {
        it('Should return option if no Product is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedProduct(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Product for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedProduct(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Product is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedProduct(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
