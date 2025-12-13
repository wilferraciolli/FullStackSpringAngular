import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasWrapper } from './canvas-wrapper';

describe('CanvasWrapper', () => {
  let component: CanvasWrapper;
  let fixture: ComponentFixture<CanvasWrapper>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CanvasWrapper]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CanvasWrapper);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
