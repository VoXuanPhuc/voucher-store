import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SildebarUserComponent } from './sildebar-user.component';

describe('SildebarUserComponent', () => {
  let component: SildebarUserComponent;
  let fixture: ComponentFixture<SildebarUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SildebarUserComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SildebarUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
