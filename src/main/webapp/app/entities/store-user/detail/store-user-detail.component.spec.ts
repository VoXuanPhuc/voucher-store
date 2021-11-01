import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { StoreUserDetailComponent } from './store-user-detail.component';

describe('Component Tests', () => {
  describe('StoreUser Management Detail Component', () => {
    let comp: StoreUserDetailComponent;
    let fixture: ComponentFixture<StoreUserDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [StoreUserDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ storeUser: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(StoreUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(StoreUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load storeUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.storeUser).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
