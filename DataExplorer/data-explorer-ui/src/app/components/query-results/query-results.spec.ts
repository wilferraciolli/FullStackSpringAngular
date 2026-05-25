import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryResults } from './query-results';

describe('QueryResults', () => {
  let component: QueryResults;
  let fixture: ComponentFixture<QueryResults>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QueryResults],
    }).compileComponents();

    fixture = TestBed.createComponent(QueryResults);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
