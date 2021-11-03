jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VoucherCodeService } from '../service/voucher-code.service';
import { IVoucherCode, VoucherCode } from '../voucher-code.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';
import { IMyOrder } from 'app/entities/my-order/my-order.model';
import { MyOrderService } from 'app/entities/my-order/service/my-order.service';

import { VoucherCodeUpdateComponent } from './voucher-code-update.component';

describe('Component Tests', () => {
  describe('VoucherCode Management Update Component', () => {
    let comp: VoucherCodeUpdateComponent;
    let fixture: ComponentFixture<VoucherCodeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let voucherCodeService: VoucherCodeService;
    let voucherService: VoucherService;
    let myOrderService: MyOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherCodeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VoucherCodeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherCodeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      voucherCodeService = TestBed.inject(VoucherCodeService);
      voucherService = TestBed.inject(VoucherService);
      myOrderService = TestBed.inject(MyOrderService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Voucher query and add missing value', () => {
        const voucherCode: IVoucherCode = { id: 456 };
        const voucher: IVoucher = { id: 92936 };
        voucherCode.voucher = voucher;

        const voucherCollection: IVoucher[] = [{ id: 89069 }];
        jest.spyOn(voucherService, 'query').mockReturnValue(of(new HttpResponse({ body: voucherCollection })));
        const additionalVouchers = [voucher];
        const expectedCollection: IVoucher[] = [...additionalVouchers, ...voucherCollection];
        jest.spyOn(voucherService, 'addVoucherToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ voucherCode });
        comp.ngOnInit();

        expect(voucherService.query).toHaveBeenCalled();
        expect(voucherService.addVoucherToCollectionIfMissing).toHaveBeenCalledWith(voucherCollection, ...additionalVouchers);
        expect(comp.vouchersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call MyOrder query and add missing value', () => {
        const voucherCode: IVoucherCode = { id: 456 };
        const order: IMyOrder = { id: 26783 };
        voucherCode.order = order;

        const myOrderCollection: IMyOrder[] = [{ id: 68558 }];
        jest.spyOn(myOrderService, 'query').mockReturnValue(of(new HttpResponse({ body: myOrderCollection })));
        const additionalMyOrders = [order];
        const expectedCollection: IMyOrder[] = [...additionalMyOrders, ...myOrderCollection];
        jest.spyOn(myOrderService, 'addMyOrderToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ voucherCode });
        comp.ngOnInit();

        expect(myOrderService.query).toHaveBeenCalled();
        expect(myOrderService.addMyOrderToCollectionIfMissing).toHaveBeenCalledWith(myOrderCollection, ...additionalMyOrders);
        expect(comp.myOrdersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const voucherCode: IVoucherCode = { id: 456 };
        const voucher: IVoucher = { id: 63084 };
        voucherCode.voucher = voucher;
        const order: IMyOrder = { id: 17771 };
        voucherCode.order = order;

        activatedRoute.data = of({ voucherCode });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(voucherCode));
        expect(comp.vouchersSharedCollection).toContain(voucher);
        expect(comp.myOrdersSharedCollection).toContain(order);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherCode>>();
        const voucherCode = { id: 123 };
        jest.spyOn(voucherCodeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherCode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucherCode }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(voucherCodeService.update).toHaveBeenCalledWith(voucherCode);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherCode>>();
        const voucherCode = new VoucherCode();
        jest.spyOn(voucherCodeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherCode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucherCode }));
        saveSubject.complete();

        // THEN
        expect(voucherCodeService.create).toHaveBeenCalledWith(voucherCode);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherCode>>();
        const voucherCode = { id: 123 };
        jest.spyOn(voucherCodeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherCode });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(voucherCodeService.update).toHaveBeenCalledWith(voucherCode);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackVoucherById', () => {
        it('Should return tracked Voucher primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVoucherById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackMyOrderById', () => {
        it('Should return tracked MyOrder primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMyOrderById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
