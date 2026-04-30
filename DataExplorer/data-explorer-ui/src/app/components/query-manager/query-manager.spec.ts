import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QueryManager } from './query-manager';

describe('QueryManager', () => {
  let component: QueryManager;
  let fixture: ComponentFixture<QueryManager>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [QueryManager],
    }).compileComponents();

    fixture = TestBed.createComponent(QueryManager);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
