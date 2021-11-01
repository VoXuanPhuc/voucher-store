import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VoucherCodeService } from '../service/voucher-code.service';

import { VoucherCodeComponent } from './voucher-code.component';

describe('Component Tests', () => {
  describe('VoucherCode Management Component', () => {
    let comp: VoucherCodeComponent;
    let fixture: ComponentFixture<VoucherCodeComponent>;
    let service: VoucherCodeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherCodeComponent],
      })
        .overrideTemplate(VoucherCodeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherCodeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VoucherCodeService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.voucherCodes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
