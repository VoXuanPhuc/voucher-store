import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { VoucherImageService } from '../service/voucher-image.service';

import { VoucherImageComponent } from './voucher-image.component';

describe('Component Tests', () => {
  describe('VoucherImage Management Component', () => {
    let comp: VoucherImageComponent;
    let fixture: ComponentFixture<VoucherImageComponent>;
    let service: VoucherImageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VoucherImageComponent],
      })
        .overrideTemplate(VoucherImageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VoucherImageComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(VoucherImageService);

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
      expect(comp.voucherImages?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
