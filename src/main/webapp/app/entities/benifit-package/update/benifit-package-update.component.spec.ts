jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { BenifitPackageService } from '../service/benifit-package.service';
import { IBenifitPackage, BenifitPackage } from '../benifit-package.model';

import { BenifitPackageUpdateComponent } from './benifit-package-update.component';

describe('Component Tests', () => {
  describe('BenifitPackage Management Update Component', () => {
    let comp: BenifitPackageUpdateComponent;
    let fixture: ComponentFixture<BenifitPackageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let benifitPackageService: BenifitPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [BenifitPackageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(BenifitPackageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BenifitPackageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      benifitPackageService = TestBed.inject(BenifitPackageService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const benifitPackage: IBenifitPackage = { id: 456 };

        activatedRoute.data = of({ benifitPackage });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(benifitPackage));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BenifitPackage>>();
        const benifitPackage = { id: 123 };
        jest.spyOn(benifitPackageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ benifitPackage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: benifitPackage }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(benifitPackageService.update).toHaveBeenCalledWith(benifitPackage);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BenifitPackage>>();
        const benifitPackage = new BenifitPackage();
        jest.spyOn(benifitPackageService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ benifitPackage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: benifitPackage }));
        saveSubject.complete();

        // THEN
        expect(benifitPackageService.create).toHaveBeenCalledWith(benifitPackage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<BenifitPackage>>();
        const benifitPackage = { id: 123 };
        jest.spyOn(benifitPackageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ benifitPackage });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(benifitPackageService.update).toHaveBeenCalledWith(benifitPackage);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
