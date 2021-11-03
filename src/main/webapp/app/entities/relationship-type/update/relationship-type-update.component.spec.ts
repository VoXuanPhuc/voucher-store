jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RelationshipTypeService } from '../service/relationship-type.service';
import { IRelationshipType, RelationshipType } from '../relationship-type.model';

import { RelationshipTypeUpdateComponent } from './relationship-type-update.component';

describe('Component Tests', () => {
  describe('RelationshipType Management Update Component', () => {
    let comp: RelationshipTypeUpdateComponent;
    let fixture: ComponentFixture<RelationshipTypeUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let relationshipTypeService: RelationshipTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RelationshipTypeUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RelationshipTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelationshipTypeUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      relationshipTypeService = TestBed.inject(RelationshipTypeService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const relationshipType: IRelationshipType = { id: 456 };

        activatedRoute.data = of({ relationshipType });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(relationshipType));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<RelationshipType>>();
        const relationshipType = { id: 123 };
        jest.spyOn(relationshipTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ relationshipType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: relationshipType }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(relationshipTypeService.update).toHaveBeenCalledWith(relationshipType);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<RelationshipType>>();
        const relationshipType = new RelationshipType();
        jest.spyOn(relationshipTypeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ relationshipType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: relationshipType }));
        saveSubject.complete();

        // THEN
        expect(relationshipTypeService.create).toHaveBeenCalledWith(relationshipType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<RelationshipType>>();
        const relationshipType = { id: 123 };
        jest.spyOn(relationshipTypeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ relationshipType });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(relationshipTypeService.update).toHaveBeenCalledWith(relationshipType);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
