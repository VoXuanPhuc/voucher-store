import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MyOrderService } from '../service/my-order.service';

import { MyOrderComponent } from './my-order.component';

describe('Component Tests', () => {
  describe('MyOrder Management Component', () => {
    let comp: MyOrderComponent;
    let fixture: ComponentFixture<MyOrderComponent>;
    let service: MyOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [MyOrderComponent],
      })
        .overrideTemplate(MyOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MyOrderComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(MyOrderService);

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
      expect(comp.myOrders?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
