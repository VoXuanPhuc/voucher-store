jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { EventService } from '../service/event.service';
import { IEvent, Event } from '../event.model';
import { IStore } from 'app/entities/store/store.model';
import { StoreService } from 'app/entities/store/service/store.service';

import { EventUpdateComponent } from './event-update.component';

describe('Component Tests', () => {
  describe('Event Management Update Component', () => {
    let comp: EventUpdateComponent;
    let fixture: ComponentFixture<EventUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let eventService: EventService;
    let storeService: StoreService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [EventUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(EventUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EventUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      eventService = TestBed.inject(EventService);
      storeService = TestBed.inject(StoreService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Store query and add missing value', () => {
        const event: IEvent = { id: 456 };
        const store: IStore = { id: 26119 };
        event.store = store;

        const storeCollection: IStore[] = [{ id: 44918 }];
        jest.spyOn(storeService, 'query').mockReturnValue(of(new HttpResponse({ body: storeCollection })));
        const additionalStores = [store];
        const expectedCollection: IStore[] = [...additionalStores, ...storeCollection];
        jest.spyOn(storeService, 'addStoreToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ event });
        comp.ngOnInit();

        expect(storeService.query).toHaveBeenCalled();
        expect(storeService.addStoreToCollectionIfMissing).toHaveBeenCalledWith(storeCollection, ...additionalStores);
        expect(comp.storesSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const event: IEvent = { id: 456 };
        const store: IStore = { id: 60741 };
        event.store = store;

        activatedRoute.data = of({ event });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(event));
        expect(comp.storesSharedCollection).toContain(store);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Event>>();
        const event = { id: 123 };
        jest.spyOn(eventService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ event });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: event }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(eventService.update).toHaveBeenCalledWith(event);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Event>>();
        const event = new Event();
        jest.spyOn(eventService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ event });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: event }));
        saveSubject.complete();

        // THEN
        expect(eventService.create).toHaveBeenCalledWith(event);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<Event>>();
        const event = { id: 123 };
        jest.spyOn(eventService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ event });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(eventService.update).toHaveBeenCalledWith(event);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
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
