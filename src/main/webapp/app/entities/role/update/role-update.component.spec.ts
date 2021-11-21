jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { RoleService } from '../service/role.service';
import { IRole, Role } from '../role.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { MyUserService } from 'app/entities/my-user/service/my-user.service';

import { RoleUpdateComponent } from './role-update.component';

describe('Component Tests', () => {
  describe('Role Management Update Component', () => {
    let comp: RoleUpdateComponent;
    let fixture: ComponentFixture<RoleUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let roleService: RoleService;
    let myUserService: MyUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RoleUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(RoleUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RoleUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      roleService = TestBed.inject(RoleService);
      myUserService = TestBed.inject(MyUserService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call MyUser query and add missing value', () => {
        const role: IRole = { id: 456 };
        const users: IMyUser[] = [{ id: 64042 }];
        role.users = users;

        const myUserCollection: IMyUser[] = [{ id: 55540 }];
        jest.spyOn(myUserService, 'query').mockReturnValue(of(new HttpResponse({ body: myUserCollection })));
        const additionalMyUsers = [...users];
        const expectedCollection: IMyUser[] = [...additionalMyUsers, ...myUserCollection];
        jest.spyOn(myUserService, 'addMyUserToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ role });
        comp.ngOnInit();

        expect(myUserService.query).toHaveBeenCalled();
        expect(myUserService.addMyUserToCollectionIfMissing).toHaveBeenCalledWith(myUserCollection, ...additionalMyUsers);
        expect(comp.myUsersSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const role: IRole = { id: 456 };
        const users: IMyUser = { id: 80172 };
        role.users = [users];

        activatedRoute.data = of({ role });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(role));
        expect(comp.myUsersSharedCollection).toContain(users);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Role>>();
        const role = { id: 123 };
        jest.spyOn(roleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ role });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: role }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(roleService.update).toHaveBeenCalledWith(role);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Role>>();
        const role = new Role();
        jest.spyOn(roleService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ role });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: role }));
        saveSubject.complete();

        // THEN
        expect(roleService.create).toHaveBeenCalledWith(role);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Role>>();
        const role = { id: 123 };
        jest.spyOn(roleService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ role });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(roleService.update).toHaveBeenCalledWith(role);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackMyUserById', () => {
        it('Should return tracked MyUser primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackMyUserById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedMyUser', () => {
        it('Should return option if no MyUser is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedMyUser(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected MyUser for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedMyUser(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this MyUser is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedMyUser(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
