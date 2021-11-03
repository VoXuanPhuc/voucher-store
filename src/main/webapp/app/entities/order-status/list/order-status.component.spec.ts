import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { OrderStatusService } from '../service/order-status.service';

import { OrderStatusComponent } from './order-status.component';

describe('Component Tests', () => {
  describe('OrderStatus Management Component', () => {
    let comp: OrderStatusComponent;
    let fixture: ComponentFixture<OrderStatusComponent>;
    let service: OrderStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [OrderStatusComponent],
      })
        .overrideTemplate(OrderStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OrderStatusComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(OrderStatusService);

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
      expect(comp.orderStatuses?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
