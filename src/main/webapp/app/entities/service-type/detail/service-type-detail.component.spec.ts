import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiceTypeDetailComponent } from './service-type-detail.component';

describe('Component Tests', () => {
  describe('ServiceType Management Detail Component', () => {
    let comp: ServiceTypeDetailComponent;
    let fixture: ComponentFixture<ServiceTypeDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ServiceTypeDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ serviceType: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ServiceTypeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceTypeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceType on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceType).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
