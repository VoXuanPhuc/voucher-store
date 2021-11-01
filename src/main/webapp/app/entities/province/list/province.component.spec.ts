import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ProvinceService } from '../service/province.service';

import { ProvinceComponent } from './province.component';

describe('Component Tests', () => {
  describe('Province Management Component', () => {
    let comp: ProvinceComponent;
    let fixture: ComponentFixture<ProvinceComponent>;
    let service: ProvinceService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ProvinceComponent],
      })
        .overrideTemplate(ProvinceComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProvinceComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ProvinceService);

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
      expect(comp.provinces?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
