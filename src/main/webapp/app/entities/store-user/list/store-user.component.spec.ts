import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { StoreUserService } from '../service/store-user.service';

import { StoreUserComponent } from './store-user.component';

describe('Component Tests', () => {
  describe('StoreUser Management Component', () => {
    let comp: StoreUserComponent;
    let fixture: ComponentFixture<StoreUserComponent>;
    let service: StoreUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [StoreUserComponent],
      })
        .overrideTemplate(StoreUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(StoreUserComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(StoreUserService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.storeUsers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
