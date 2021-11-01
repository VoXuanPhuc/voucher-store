jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { StoreUserService } from '../service/store-user.service';
import { IStoreUser, StoreUser } from '../store-user.model';
import { IRelationshipType } from 'app/entities/relationship-type/relationship-type.model';
import { RelationshipTypeService } from 'app/entities/relationship-type/service/relationship-type.service';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';
import { IStore } from 'app/entities/store/store.model';
import { StoreService } from 'app/entities/store/service/store.service';

import { StoreUserUpdateComponent } from './store-user-update.component';

describe('Component Tests', () => {
  describe('StoreUser Management Update Component', () => {
    let comp: StoreUserUpdateComponent;
    let fixture: ComponentFixture<StoreUserUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let storeUserService: StoreUserService;
    let relationshipTypeService: RelationshipTypeService;
    let myUserService: MyUserService;
    let storeService: StoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StoreUserUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(StoreUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreUserUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      storeUserService = TestBed.inject(StoreUserService);
      relationshipTypeService = TestBed.inject(RelationshipTypeService);
      myUserService = TestBed.inject(MyUserService);
      storeService = TestBed.inject(StoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call RelationshipType query and add missing value', () => {
        const storeUser: IStoreUser = { id: 456 };
        const type: IRelationshipType = { id: 80668 };
        storeUser.type = type;

        const relationshipTypeCollection: IRelationshipType[] = [{ id: 14597 }];
        jest.spyOn(relationshipTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: relationshipTypeCollection })));
        const additionalRelationshipTypes = [type];
        const expectedCollection: IRelationshipType[] = [...additionalRelationshipTypes, ...relationshipTypeCollection];
        jest.spyOn(relationshipTypeService, 'addRelationshipTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        expect(relationshipTypeService.query).toHaveBeenCalled();
        expect(relationshipTypeService.addRelationshipTypeToCollectionIfMissing).toHaveBeenCalledWith(
          relationshipTypeCollection,
          ...additionalRelationshipTypes
        );
        expect(comp.relationshipTypesSharedCollection).toEqual(expectedCollection);
      });

      it('Should call MyUser query and add missing value', () => {
        const storeUser: IStoreUser = { id: 456 };
        const user: IMyUser = { id: 5630 };
        storeUser.user = user;

        const myUserCollection: IMyUser[] = [{ id: 30663 }];
        jest.spyOn(myUserService, 'query').mockReturnValue(of(new HttpResponse({ body: myUserCollection })));
        const additionalMyUsers = [user];
        const expectedCollection: IMyUser[] = [...additionalMyUsers, ...myUserCollection];
        jest.spyOn(myUserService, 'addMyUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        expect(myUserService.query).toHaveBeenCalled();
        expect(myUserService.addMyUserToCollectionIfMissing).toHaveBeenCalledWith(myUserCollection, ...additionalMyUsers);
        expect(comp.myUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Store query and add missing value', () => {
        const storeUser: IStoreUser = { id: 456 };
        const store: IStore = { id: 19361 };
        storeUser.store = store;

        const storeCollection: IStore[] = [{ id: 35914 }];
        jest.spyOn(storeService, 'query').mockReturnValue(of(new HttpResponse({ body: storeCollection })));
        const additionalStores = [store];
        const expectedCollection: IStore[] = [...additionalStores, ...storeCollection];
        jest.spyOn(storeService, 'addStoreToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        expect(storeService.query).toHaveBeenCalled();
        expect(storeService.addStoreToCollectionIfMissing).toHaveBeenCalledWith(storeCollection, ...additionalStores);
        expect(comp.storesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const storeUser: IStoreUser = { id: 456 };
        const type: IRelationshipType = { id: 57530 };
        storeUser.type = type;
        const user: IMyUser = { id: 43302 };
        storeUser.user = user;
        const store: IStore = { id: 39200 };
        storeUser.store = store;

        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(storeUser));
        expect(comp.relationshipTypesSharedCollection).toContain(type);
        expect(comp.myUsersSharedCollection).toContain(user);
        expect(comp.storesSharedCollection).toContain(store);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StoreUser>>();
        const storeUser = { id: 123 };
        jest.spyOn(storeUserService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: storeUser }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(storeUserService.update).toHaveBeenCalledWith(storeUser);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StoreUser>>();
        const storeUser = new StoreUser();
        jest.spyOn(storeUserService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: storeUser }));
        saveSubject.complete();

        // THEN
        expect(storeUserService.create).toHaveBeenCalledWith(storeUser);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<StoreUser>>();
        const storeUser = { id: 123 };
        jest.spyOn(storeUserService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ storeUser });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(storeUserService.update).toHaveBeenCalledWith(storeUser);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackRelationshipTypeById', () => {
        it('Should return tracked RelationshipType primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackRelationshipTypeById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackMyUserById', () => {
        it('Should return tracked MyUser primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMyUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackStoreById', () => {
        it('Should return tracked Store primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackStoreById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
