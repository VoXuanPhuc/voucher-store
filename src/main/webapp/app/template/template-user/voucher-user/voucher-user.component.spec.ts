import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoucherUserComponent } from './voucher-user.component';

describe('VoucherUserComponent', () => {
  let component: VoucherUserComponent;
  let fixture: ComponentFixture<VoucherUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VoucherUserComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoucherUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
