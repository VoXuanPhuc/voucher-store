import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BenifitPackageDetailComponent } from './benifit-package-detail.component';

describe('Component Tests', () => {
  describe('BenifitPackage Management Detail Component', () => {
    let comp: BenifitPackageDetailComponent;
    let fixture: ComponentFixture<BenifitPackageDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [BenifitPackageDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ benifitPackage: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(BenifitPackageDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BenifitPackageDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load benifitPackage on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.benifitPackage).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
