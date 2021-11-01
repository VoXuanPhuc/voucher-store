import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GiftDetailComponent } from './gift-detail.component';

describe('Component Tests', () => {
  describe('Gift Management Detail Component', () => {
    let comp: GiftDetailComponent;
    let fixture: ComponentFixture<GiftDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [GiftDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ gift: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(GiftDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(GiftDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load gift on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.gift).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
