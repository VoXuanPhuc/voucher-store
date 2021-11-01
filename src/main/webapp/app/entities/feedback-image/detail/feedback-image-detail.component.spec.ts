import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FeedbackImageDetailComponent } from './feedback-image-detail.component';

describe('Component Tests', () => {
  describe('FeedbackImage Management Detail Component', () => {
    let comp: FeedbackImageDetailComponent;
    let fixture: ComponentFixture<FeedbackImageDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FeedbackImageDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ feedbackImage: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FeedbackImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FeedbackImageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load feedbackImage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.feedbackImage).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
