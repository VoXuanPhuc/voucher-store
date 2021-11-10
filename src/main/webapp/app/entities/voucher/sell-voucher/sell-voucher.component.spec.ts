import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellVoucherComponent } from './sell-voucher.component';

describe('SellVoucherComponent', () => {
  let component: SellVoucherComponent;
  let fixture: ComponentFixture<SellVoucherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SellVoucherComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SellVoucherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
