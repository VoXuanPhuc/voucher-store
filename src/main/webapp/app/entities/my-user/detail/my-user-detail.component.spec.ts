import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyUserDetailComponent } from './my-user-detail.component';

describe('Component Tests', () => {
  describe('MyUser Management Detail Component', () => {
    let comp: MyUserDetailComponent;
    let fixture: ComponentFixture<MyUserDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MyUserDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ myUser: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MyUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MyUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load myUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.myUser).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
