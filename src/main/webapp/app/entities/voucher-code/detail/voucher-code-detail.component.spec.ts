import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoucherCodeDetailComponent } from './voucher-code-detail.component';

describe('Component Tests', () => {
  describe('VoucherCode Management Detail Component', () => {
    let comp: VoucherCodeDetailComponent;
    let fixture: ComponentFixture<VoucherCodeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VoucherCodeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ voucherCode: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VoucherCodeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoucherCodeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voucherCode on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voucherCode).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
