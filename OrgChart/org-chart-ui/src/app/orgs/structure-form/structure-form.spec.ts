import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StructureForm } from './structure-form';

describe('StructureForm', () => {
  let component: StructureForm;
  let fixture: ComponentFixture<StructureForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StructureForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StructureForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
