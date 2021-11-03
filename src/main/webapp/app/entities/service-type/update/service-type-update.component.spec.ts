jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ServiceTypeService } from '../service/service-type.service';
import { IServiceType, ServiceType } from '../service-type.model';

import { ServiceTypeUpdateComponent } from './service-type-update.component';

describe('Component Tests', () => {
  describe('ServiceType Management Update Component', () => {
    let comp: ServiceTypeUpdateComponent;
    let fixture: ComponentFixture<ServiceTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let serviceTypeService: ServiceTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ServiceTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ServiceTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      serviceTypeService = TestBed.inject(ServiceTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const serviceType: IServiceType = { id: 456 };

        activatedRoute.data = of({ serviceType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(serviceType));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ServiceType>>();
        const serviceType = { id: 123 };
        jest.spyOn(serviceTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ serviceType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: serviceType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(serviceTypeService.update).toHaveBeenCalledWith(serviceType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ServiceType>>();
        const serviceType = new ServiceType();
        jest.spyOn(serviceTypeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ serviceType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: serviceType }));
        saveSubject.complete();

        // THEN
        expect(serviceTypeService.create).toHaveBeenCalledWith(serviceType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ServiceType>>();
        const serviceType = { id: 123 };
        jest.spyOn(serviceTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ serviceType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(serviceTypeService.update).toHaveBeenCalledWith(serviceType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
