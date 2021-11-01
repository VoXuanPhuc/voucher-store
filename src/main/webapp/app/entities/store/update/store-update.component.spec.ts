jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StoreService } from '../service/store.service';
import { IStore, Store } from '../store.model';
import { IAddress } from 'app/entities/address/address.model';
import { AddressService } from 'app/entities/address/service/address.service';
import { IBenifitPackage } from 'app/entities/benifit-package/benifit-package.model';
import { BenifitPackageService } from 'app/entities/benifit-package/service/benifit-package.service';

import { StoreUpdateComponent } from './store-update.component';

describe('Component Tests', () => {
  describe('Store Management Update Component', () => {
    let comp: StoreUpdateComponent;
    let fixture: ComponentFixture<StoreUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let storeService: StoreService;
    let addressService: AddressService;
    let benifitPackageService: BenifitPackageService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StoreUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(StoreUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      storeService = TestBed.inject(StoreService);
      addressService = TestBed.inject(AddressService);
      benifitPackageService = TestBed.inject(BenifitPackageService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call address query and add missing value', () => {
        const store: IStore = { id: 456 };
        const address: IAddress = { id: 30497 };
        store.address = address;

        const addressCollection: IAddress[] = [{ id: 62222 }];
        jest.spyOn(addressService, 'query').mockReturnValue(of(new HttpResponse({ body: addressCollection })));
        const expectedCollection: IAddress[] = [address, ...addressCollection];
        jest.spyOn(addressService, 'addAddressToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ store });
        comp.ngOnInit();

        expect(addressService.query).toHaveBeenCalled();
        expect(addressService.addAddressToCollectionIfMissing).toHaveBeenCalledWith(addressCollection, address);
        expect(comp.addressesCollection).toEqual(expectedCollection);
      });

      it('Should call BenifitPackage query and add missing value', () => {
        const store: IStore = { id: 456 };
        const pack: IBenifitPackage = { id: 66827 };
        store.pack = pack;

        const benifitPackageCollection: IBenifitPackage[] = [{ id: 73507 }];
        jest.spyOn(benifitPackageService, 'query').mockReturnValue(of(new HttpResponse({ body: benifitPackageCollection })));
        const additionalBenifitPackages = [pack];
        const expectedCollection: IBenifitPackage[] = [...additionalBenifitPackages, ...benifitPackageCollection];
        jest.spyOn(benifitPackageService, 'addBenifitPackageToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ store });
        comp.ngOnInit();

        expect(benifitPackageService.query).toHaveBeenCalled();
        expect(benifitPackageService.addBenifitPackageToCollectionIfMissing).toHaveBeenCalledWith(
          benifitPackageCollection,
          ...additionalBenifitPackages
        );
        expect(comp.benifitPackagesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const store: IStore = { id: 456 };
        const address: IAddress = { id: 10851 };
        store.address = address;
        const pack: IBenifitPackage = { id: 57461 };
        store.pack = pack;

        activatedRoute.data = of({ store });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(store));
        expect(comp.addressesCollection).toContain(address);
        expect(comp.benifitPackagesSharedCollection).toContain(pack);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Store>>();
        const store = { id: 123 };
        jest.spyOn(storeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ store });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: store }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(storeService.update).toHaveBeenCalledWith(store);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Store>>();
        const store = new Store();
        jest.spyOn(storeService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ store });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: store }));
        saveSubject.complete();

        // THEN
        expect(storeService.create).toHaveBeenCalledWith(store);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Store>>();
        const store = { id: 123 };
        jest.spyOn(storeService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ store });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(storeService.update).toHaveBeenCalledWith(store);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackAddressById', () => {
        it('Should return tracked Address primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAddressById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackBenifitPackageById', () => {
        it('Should return tracked BenifitPackage primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackBenifitPackageById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
