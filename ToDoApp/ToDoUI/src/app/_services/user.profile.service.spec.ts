import { TestBed } from '@angular/core/testing';

import { UserProfileService } from './user.profile.service';

describe('User.ProfileService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserProfileService = TestBed.get(UserProfileService);
    expect(service).toBeTruthy();
  });
});
