jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VillageService } from '../service/village.service';
import { IVillage, Village } from '../village.model';
import { IDistrict } from 'app/entities/district/district.model';
import { DistrictService } from 'app/entities/district/service/district.service';

import { VillageUpdateComponent } from './village-update.component';

describe('Component Tests', () => {
  describe('Village Management Update Component', () => {
    let comp: VillageUpdateComponent;
    let fixture: ComponentFixture<VillageUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let villageService: VillageService;
    let districtService: DistrictService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VillageUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VillageUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VillageUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      villageService = TestBed.inject(VillageService);
      districtService = TestBed.inject(DistrictService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call District query and add missing value', () => {
        const village: IVillage = { id: 456 };
        const district: IDistrict = { id: 99595 };
        village.district = district;

        const districtCollection: IDistrict[] = [{ id: 64929 }];
        jest.spyOn(districtService, 'query').mockReturnValue(of(new HttpResponse({ body: districtCollection })));
        const additionalDistricts = [district];
        const expectedCollection: IDistrict[] = [...additionalDistricts, ...districtCollection];
        jest.spyOn(districtService, 'addDistrictToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ village });
        comp.ngOnInit();

        expect(districtService.query).toHaveBeenCalled();
        expect(districtService.addDistrictToCollectionIfMissing).toHaveBeenCalledWith(districtCollection, ...additionalDistricts);
        expect(comp.districtsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const village: IVillage = { id: 456 };
        const district: IDistrict = { id: 52377 };
        village.district = district;

        activatedRoute.data = of({ village });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(village));
        expect(comp.districtsSharedCollection).toContain(district);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Village>>();
        const village = { id: 123 };
        jest.spyOn(villageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ village });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: village }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(villageService.update).toHaveBeenCalledWith(village);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Village>>();
        const village = new Village();
        jest.spyOn(villageService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ village });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: village }));
        saveSubject.complete();

        // THEN
        expect(villageService.create).toHaveBeenCalledWith(village);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Village>>();
        const village = { id: 123 };
        jest.spyOn(villageService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ village });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(villageService.update).toHaveBeenCalledWith(village);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackDistrictById', () => {
        it('Should return tracked District primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackDistrictById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
