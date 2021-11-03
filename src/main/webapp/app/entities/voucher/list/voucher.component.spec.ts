import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VoucherService } from '../service/voucher.service';

import { VoucherComponent } from './voucher.component';

describe('Component Tests', () => {
  describe('Voucher Management Component', () => {
    let comp: VoucherComponent;
    let fixture: ComponentFixture<VoucherComponent>;
    let service: VoucherService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherComponent],
      })
        .overrideTemplate(VoucherComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VoucherService);

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
      expect(comp.vouchers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
