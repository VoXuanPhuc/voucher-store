import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeedbackVoucherComponent } from './feedback-voucher.component';

describe('FeedbackVoucherComponent', () => {
  let component: FeedbackVoucherComponent;
  let fixture: ComponentFixture<FeedbackVoucherComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FeedbackVoucherComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FeedbackVoucherComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
