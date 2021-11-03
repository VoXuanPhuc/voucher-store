import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoucherStatusDetailComponent } from './voucher-status-detail.component';

describe('Component Tests', () => {
  describe('VoucherStatus Management Detail Component', () => {
    let comp: VoucherStatusDetailComponent;
    let fixture: ComponentFixture<VoucherStatusDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VoucherStatusDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ voucherStatus: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VoucherStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoucherStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voucherStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voucherStatus).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
