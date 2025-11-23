import { inject } from '@angular/core';
import { signalStore, withState, withComputed, withMethods, patchState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { pipe, tap, switchMap, catchError, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { computed } from '@angular/core';
import {User} from '../user-logged-on.interface';

// 1. Define the Shape of the State
type UserState = {
  user: User | null;
  isLoading: boolean;
  error: string | null;
};

// 2. Set Initial State
const initialState: UserState = {
  user: null,
  isLoading: false,
  error: null,
};

export const UserStore = signalStore(
  { providedIn: 'root' }, // Makes it a global singleton service
  withState(initialState),

  // 3. Computed Signals (Derived values that update automatically)
  withComputed((store) => ({
    isLoggedIn: computed(() => !!store.user()),
    isAdmin: computed(() => store.user()?.role === 'ADMIN'),
    // Example for your Org Chart: "Can I see private nodes?"
    canViewPrivateData: computed(() => !!store.user()?.managerId || store.user()?.role === 'ADMIN'),
  })),

  // 4. Methods (Actions to update state)
  withMethods((store, http = inject(HttpClient)) => ({

    // Synchronous Method: Logout
    logout() {
      patchState(store, { user: null, error: null });
      localStorage.removeItem('user_token'); // Cleanup
    },
    login(selectedUser: User){
      patchState(store, { user: selectedUser });
    }
  }))
);
