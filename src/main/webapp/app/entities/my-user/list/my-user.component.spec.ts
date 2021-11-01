import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MyUserService } from '../service/my-user.service';

import { MyUserComponent } from './my-user.component';

describe('Component Tests', () => {
  describe('MyUser Management Component', () => {
    let comp: MyUserComponent;
    let fixture: ComponentFixture<MyUserComponent>;
    let service: MyUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MyUserComponent],
      })
        .overrideTemplate(MyUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyUserComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(MyUserService);

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
      expect(comp.myUsers?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
