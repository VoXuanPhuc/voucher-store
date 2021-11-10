import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotVoucherComponent } from './hot-voucher.component';

describe('HotVoucherComponent', () => {
  let component: HotVoucherComponent;
  let fixture: ComponentFixture<HotVoucherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [HotVoucherComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HotVoucherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
