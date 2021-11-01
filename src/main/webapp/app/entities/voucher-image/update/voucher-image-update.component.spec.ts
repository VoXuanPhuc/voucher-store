jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VoucherImageService } from '../service/voucher-image.service';
import { IVoucherImage, VoucherImage } from '../voucher-image.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';

import { VoucherImageUpdateComponent } from './voucher-image-update.component';

describe('Component Tests', () => {
  describe('VoucherImage Management Update Component', () => {
    let comp: VoucherImageUpdateComponent;
    let fixture: ComponentFixture<VoucherImageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let voucherImageService: VoucherImageService;
    let voucherService: VoucherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherImageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VoucherImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherImageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      voucherImageService = TestBed.inject(VoucherImageService);
      voucherService = TestBed.inject(VoucherService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Voucher query and add missing value', () => {
        const voucherImage: IVoucherImage = { id: 456 };
        const voucher: IVoucher = { id: 90395 };
        voucherImage.voucher = voucher;

        const voucherCollection: IVoucher[] = [{ id: 4292 }];
        jest.spyOn(voucherService, 'query').mockReturnValue(of(new HttpResponse({ body: voucherCollection })));
        const additionalVouchers = [voucher];
        const expectedCollection: IVoucher[] = [...additionalVouchers, ...voucherCollection];
        jest.spyOn(voucherService, 'addVoucherToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ voucherImage });
        comp.ngOnInit();

        expect(voucherService.query).toHaveBeenCalled();
        expect(voucherService.addVoucherToCollectionIfMissing).toHaveBeenCalledWith(voucherCollection, ...additionalVouchers);
        expect(comp.vouchersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const voucherImage: IVoucherImage = { id: 456 };
        const voucher: IVoucher = { id: 58523 };
        voucherImage.voucher = voucher;

        activatedRoute.data = of({ voucherImage });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(voucherImage));
        expect(comp.vouchersSharedCollection).toContain(voucher);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherImage>>();
        const voucherImage = { id: 123 };
        jest.spyOn(voucherImageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherImage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucherImage }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(voucherImageService.update).toHaveBeenCalledWith(voucherImage);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherImage>>();
        const voucherImage = new VoucherImage();
        jest.spyOn(voucherImageService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherImage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: voucherImage }));
        saveSubject.complete();

        // THEN
        expect(voucherImageService.create).toHaveBeenCalledWith(voucherImage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<VoucherImage>>();
        const voucherImage = { id: 123 };
        jest.spyOn(voucherImageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ voucherImage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(voucherImageService.update).toHaveBeenCalledWith(voucherImage);
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
    });
  });
});
