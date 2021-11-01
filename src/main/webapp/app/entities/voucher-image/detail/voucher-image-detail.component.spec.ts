import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VoucherImageDetailComponent } from './voucher-image-detail.component';

describe('Component Tests', () => {
  describe('VoucherImage Management Detail Component', () => {
    let comp: VoucherImageDetailComponent;
    let fixture: ComponentFixture<VoucherImageDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [VoucherImageDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ voucherImage: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(VoucherImageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VoucherImageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load voucherImage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.voucherImage).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
