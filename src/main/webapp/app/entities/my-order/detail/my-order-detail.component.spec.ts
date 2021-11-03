import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MyOrderDetailComponent } from './my-order-detail.component';

describe('Component Tests', () => {
  describe('MyOrder Management Detail Component', () => {
    let comp: MyOrderDetailComponent;
    let fixture: ComponentFixture<MyOrderDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [MyOrderDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ myOrder: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(MyOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MyOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load myOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.myOrder).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
