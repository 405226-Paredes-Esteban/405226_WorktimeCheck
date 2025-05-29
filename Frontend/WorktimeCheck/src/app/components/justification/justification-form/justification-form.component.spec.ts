import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JustificationFormComponent } from './justification-form.component';

describe('JustificationFormComponent', () => {
  let component: JustificationFormComponent;
  let fixture: ComponentFixture<JustificationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JustificationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JustificationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
