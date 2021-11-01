jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VoucherStatusService } from '../service/voucher-status.service';
import { IVoucherStatus, VoucherStatus } from '../voucher-status.model';

import { VoucherStatusUpdateComponent } from './voucher-status-update.component';

describe('Component Tests', () => {
  describe('VoucherStatus Management Update Component', () => {
    let comp: VoucherStatusUpdateComponent;
    let fixture: ComponentFixture<VoucherStatusUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let voucherStatusService: VoucherStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherStatusUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VoucherStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherStatusUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      voucherStatusService = TestBed.inject(VoucherStatusService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const voucherStatus: IVoucherStatus = { id: 456 };

        activatedRoute.data = of({ voucherStatus });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(voucherStatus));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherStatus>>();
        const voucherStatus = { id: 123 };
        jest.spyOn(voucherStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucherStatus }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(voucherStatusService.update).toHaveBeenCalledWith(voucherStatus);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherStatus>>();
        const voucherStatus = new VoucherStatus();
        jest.spyOn(voucherStatusService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucherStatus }));
        saveSubject.complete();

        // THEN
        expect(voucherStatusService.create).toHaveBeenCalledWith(voucherStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherStatus>>();
        const voucherStatus = { id: 123 };
        jest.spyOn(voucherStatusService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherStatus });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(voucherStatusService.update).toHaveBeenCalledWith(voucherStatus);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
