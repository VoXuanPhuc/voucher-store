import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { GiftService } from '../service/gift.service';

import { GiftComponent } from './gift.component';

describe('Component Tests', () => {
  describe('Gift Management Component', () => {
    let comp: GiftComponent;
    let fixture: ComponentFixture<GiftComponent>;
    let service: GiftService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [GiftComponent],
      })
        .overrideTemplate(GiftComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(GiftComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(GiftService);

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
      expect(comp.gifts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
