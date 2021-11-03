import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ServiceTypeService } from '../service/service-type.service';

import { ServiceTypeComponent } from './service-type.component';

describe('Component Tests', () => {
  describe('ServiceType Management Component', () => {
    let comp: ServiceTypeComponent;
    let fixture: ComponentFixture<ServiceTypeComponent>;
    let service: ServiceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ServiceTypeComponent],
      })
        .overrideTemplate(ServiceTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceTypeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ServiceTypeService);

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
      expect(comp.serviceTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
