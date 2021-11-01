jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { GiftService } from '../service/gift.service';
import { IGift, Gift } from '../gift.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IVoucherCode } from 'app/entities/voucher-code/voucher-code.model';
import { VoucherCodeService } from 'app/entities/voucher-code/service/voucher-code.service';

import { GiftUpdateComponent } from './gift-update.component';

describe('Component Tests', () => {
  describe('Gift Management Update Component', () => {
    let comp: GiftUpdateComponent;
    let fixture: ComponentFixture<GiftUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let giftService: GiftService;
    let myUserService: MyUserService;
    let voucherCodeService: VoucherCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GiftUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(GiftUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GiftUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      giftService = TestBed.inject(GiftService);
      myUserService = TestBed.inject(MyUserService);
      voucherCodeService = TestBed.inject(VoucherCodeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call MyUser query and add missing value', () => {
        const gift: IGift = { id: 456 };
        const giver: IMyUser = { id: 27928 };
        gift.giver = giver;

        const myUserCollection: IMyUser[] = [{ id: 84574 }];
        jest.spyOn(myUserService, 'query').mockReturnValue(of(new HttpResponse({ body: myUserCollection })));
        const additionalMyUsers = [giver];
        const expectedCollection: IMyUser[] = [...additionalMyUsers, ...myUserCollection];
        jest.spyOn(myUserService, 'addMyUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ gift });
        comp.ngOnInit();

        expect(myUserService.query).toHaveBeenCalled();
        expect(myUserService.addMyUserToCollectionIfMissing).toHaveBeenCalledWith(myUserCollection, ...additionalMyUsers);
        expect(comp.myUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call VoucherCode query and add missing value', () => {
        const gift: IGift = { id: 456 };
        const voucher: IVoucherCode = { id: 48251 };
        gift.voucher = voucher;

        const voucherCodeCollection: IVoucherCode[] = [{ id: 10762 }];
        jest.spyOn(voucherCodeService, 'query').mockReturnValue(of(new HttpResponse({ body: voucherCodeCollection })));
        const additionalVoucherCodes = [voucher];
        const expectedCollection: IVoucherCode[] = [...additionalVoucherCodes, ...voucherCodeCollection];
        jest.spyOn(voucherCodeService, 'addVoucherCodeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ gift });
        comp.ngOnInit();

        expect(voucherCodeService.query).toHaveBeenCalled();
        expect(voucherCodeService.addVoucherCodeToCollectionIfMissing).toHaveBeenCalledWith(
          voucherCodeCollection,
          ...additionalVoucherCodes
        );
        expect(comp.voucherCodesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const gift: IGift = { id: 456 };
        const giver: IMyUser = { id: 36396 };
        gift.giver = giver;
        const voucher: IVoucherCode = { id: 82742 };
        gift.voucher = voucher;

        activatedRoute.data = of({ gift });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(gift));
        expect(comp.myUsersSharedCollection).toContain(giver);
        expect(comp.voucherCodesSharedCollection).toContain(voucher);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Gift>>();
        const gift = { id: 123 };
        jest.spyOn(giftService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ gift });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: gift }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(giftService.update).toHaveBeenCalledWith(gift);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Gift>>();
        const gift = new Gift();
        jest.spyOn(giftService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ gift });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: gift }));
        saveSubject.complete();

        // THEN
        expect(giftService.create).toHaveBeenCalledWith(gift);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Gift>>();
        const gift = { id: 123 };
        jest.spyOn(giftService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ gift });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(giftService.update).toHaveBeenCalledWith(gift);
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

      describe('trackVoucherCodeById', () => {
        it('Should return tracked VoucherCode primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackVoucherCodeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
