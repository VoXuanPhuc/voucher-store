import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { BenifitPackageService } from '../service/benifit-package.service';

import { BenifitPackageComponent } from './benifit-package.component';

describe('Component Tests', () => {
  describe('BenifitPackage Management Component', () => {
    let comp: BenifitPackageComponent;
    let fixture: ComponentFixture<BenifitPackageComponent>;
    let service: BenifitPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BenifitPackageComponent],
      })
        .overrideTemplate(BenifitPackageComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BenifitPackageComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(BenifitPackageService);

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
      expect(comp.benifitPackages?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
