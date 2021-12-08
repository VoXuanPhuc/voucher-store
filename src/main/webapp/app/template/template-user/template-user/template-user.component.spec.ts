import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TemplateUserComponent } from './template-user.component';

describe('TemplateUserComponent', () => {
  let component: TemplateUserComponent;
  let fixture: ComponentFixture<TemplateUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TemplateUserComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TemplateUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
