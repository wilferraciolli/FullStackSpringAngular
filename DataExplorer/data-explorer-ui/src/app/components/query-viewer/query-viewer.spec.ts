import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryViewer } from './query-viewer';

describe('QueryViewer', () => {
  let component: QueryViewer;
  let fixture: ComponentFixture<QueryViewer>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QueryViewer],
    }).compileComponents();

    fixture = TestBed.createComponent(QueryViewer);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
