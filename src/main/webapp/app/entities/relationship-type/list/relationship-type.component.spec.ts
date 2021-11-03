import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { RelationshipTypeService } from '../service/relationship-type.service';

import { RelationshipTypeComponent } from './relationship-type.component';

describe('Component Tests', () => {
  describe('RelationshipType Management Component', () => {
    let comp: RelationshipTypeComponent;
    let fixture: ComponentFixture<RelationshipTypeComponent>;
    let service: RelationshipTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [RelationshipTypeComponent],
      })
        .overrideTemplate(RelationshipTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RelationshipTypeComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(RelationshipTypeService);

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
      expect(comp.relationshipTypes?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
