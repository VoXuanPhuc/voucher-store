import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VoucherStatusService } from '../service/voucher-status.service';

import { VoucherStatusComponent } from './voucher-status.component';

describe('Component Tests', () => {
  describe('VoucherStatus Management Component', () => {
    let comp: VoucherStatusComponent;
    let fixture: ComponentFixture<VoucherStatusComponent>;
    let service: VoucherStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherStatusComponent],
      })
        .overrideTemplate(VoucherStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherStatusComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VoucherStatusService);

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
      expect(comp.voucherStatuses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
