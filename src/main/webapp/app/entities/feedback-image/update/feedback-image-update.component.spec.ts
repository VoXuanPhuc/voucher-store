jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FeedbackImageService } from '../service/feedback-image.service';
import { IFeedbackImage, FeedbackImage } from '../feedback-image.model';
import { IFeedback } from 'app/entities/feedback/feedback.model';
import { FeedbackService } from 'app/entities/feedback/service/feedback.service';

import { FeedbackImageUpdateComponent } from './feedback-image-update.component';

describe('Component Tests', () => {
  describe('FeedbackImage Management Update Component', () => {
    let comp: FeedbackImageUpdateComponent;
    let fixture: ComponentFixture<FeedbackImageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let feedbackImageService: FeedbackImageService;
    let feedbackService: FeedbackService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FeedbackImageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FeedbackImageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FeedbackImageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      feedbackImageService = TestBed.inject(FeedbackImageService);
      feedbackService = TestBed.inject(FeedbackService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Feedback query and add missing value', () => {
        const feedbackImage: IFeedbackImage = { id: 456 };
        const feedback: IFeedback = { id: 42448 };
        feedbackImage.feedback = feedback;

        const feedbackCollection: IFeedback[] = [{ id: 49613 }];
        jest.spyOn(feedbackService, 'query').mockReturnValue(of(new HttpResponse({ body: feedbackCollection })));
        const additionalFeedbacks = [feedback];
        const expectedCollection: IFeedback[] = [...additionalFeedbacks, ...feedbackCollection];
        jest.spyOn(feedbackService, 'addFeedbackToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ feedbackImage });
        comp.ngOnInit();

        expect(feedbackService.query).toHaveBeenCalled();
        expect(feedbackService.addFeedbackToCollectionIfMissing).toHaveBeenCalledWith(feedbackCollection, ...additionalFeedbacks);
        expect(comp.feedbacksSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const feedbackImage: IFeedbackImage = { id: 456 };
        const feedback: IFeedback = { id: 42729 };
        feedbackImage.feedback = feedback;

        activatedRoute.data = of({ feedbackImage });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(feedbackImage));
        expect(comp.feedbacksSharedCollection).toContain(feedback);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FeedbackImage>>();
        const feedbackImage = { id: 123 };
        jest.spyOn(feedbackImageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ feedbackImage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: feedbackImage }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(feedbackImageService.update).toHaveBeenCalledWith(feedbackImage);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FeedbackImage>>();
        const feedbackImage = new FeedbackImage();
        jest.spyOn(feedbackImageService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ feedbackImage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: feedbackImage }));
        saveSubject.complete();

        // THEN
        expect(feedbackImageService.create).toHaveBeenCalledWith(feedbackImage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<FeedbackImage>>();
        const feedbackImage = { id: 123 };
        jest.spyOn(feedbackImageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ feedbackImage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(feedbackImageService.update).toHaveBeenCalledWith(feedbackImage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackFeedbackById', () => {
        it('Should return tracked Feedback primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackFeedbackById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
