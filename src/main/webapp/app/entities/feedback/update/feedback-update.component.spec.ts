jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FeedbackService } from '../service/feedback.service';
import { IFeedback, Feedback } from '../feedback.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IVoucher } from 'app/entities/voucher/voucher.model';
import { VoucherService } from 'app/entities/voucher/service/voucher.service';

import { FeedbackUpdateComponent } from './feedback-update.component';

describe('Component Tests', () => {
  describe('Feedback Management Update Component', () => {
    let comp: FeedbackUpdateComponent;
    let fixture: ComponentFixture<FeedbackUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let feedbackService: FeedbackService;
    let myUserService: MyUserService;
    let voucherService: VoucherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeedbackUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FeedbackUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedbackUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      feedbackService = TestBed.inject(FeedbackService);
      myUserService = TestBed.inject(MyUserService);
      voucherService = TestBed.inject(VoucherService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call MyUser query and add missing value', () => {
        const feedback: IFeedback = { id: 456 };
        const user: IMyUser = { id: 49437 };
        feedback.user = user;

        const myUserCollection: IMyUser[] = [{ id: 64354 }];
        jest.spyOn(myUserService, 'query').mockReturnValue(of(new HttpResponse({ body: myUserCollection })));
        const additionalMyUsers = [user];
        const expectedCollection: IMyUser[] = [...additionalMyUsers, ...myUserCollection];
        jest.spyOn(myUserService, 'addMyUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ feedback });
        comp.ngOnInit();

        expect(myUserService.query).toHaveBeenCalled();
        expect(myUserService.addMyUserToCollectionIfMissing).toHaveBeenCalledWith(myUserCollection, ...additionalMyUsers);
        expect(comp.myUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Voucher query and add missing value', () => {
        const feedback: IFeedback = { id: 456 };
        const voucher: IVoucher = { id: 41813 };
        feedback.voucher = voucher;

        const voucherCollection: IVoucher[] = [{ id: 97948 }];
        jest.spyOn(voucherService, 'query').mockReturnValue(of(new HttpResponse({ body: voucherCollection })));
        const additionalVouchers = [voucher];
        const expectedCollection: IVoucher[] = [...additionalVouchers, ...voucherCollection];
        jest.spyOn(voucherService, 'addVoucherToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ feedback });
        comp.ngOnInit();

        expect(voucherService.query).toHaveBeenCalled();
        expect(voucherService.addVoucherToCollectionIfMissing).toHaveBeenCalledWith(voucherCollection, ...additionalVouchers);
        expect(comp.vouchersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const feedback: IFeedback = { id: 456 };
        const user: IMyUser = { id: 14523 };
        feedback.user = user;
        const voucher: IVoucher = { id: 38686 };
        feedback.voucher = voucher;

        activatedRoute.data = of({ feedback });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(feedback));
        expect(comp.myUsersSharedCollection).toContain(user);
        expect(comp.vouchersSharedCollection).toContain(voucher);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Feedback>>();
        const feedback = { id: 123 };
        jest.spyOn(feedbackService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ feedback });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: feedback }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(feedbackService.update).toHaveBeenCalledWith(feedback);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Feedback>>();
        const feedback = new Feedback();
        jest.spyOn(feedbackService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ feedback });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: feedback }));
        saveSubject.complete();

        // THEN
        expect(feedbackService.create).toHaveBeenCalledWith(feedback);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Feedback>>();
        const feedback = { id: 123 };
        jest.spyOn(feedbackService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ feedback });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(feedbackService.update).toHaveBeenCalledWith(feedback);
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
