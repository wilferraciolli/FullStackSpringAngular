import { inject } from '@angular/core';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';

import { catchError, EMPTY, from, pipe, switchMap, tap } from 'rxjs';

import { DataArea } from '../components/query-builder/query-builder';
import { GraphqlService } from './graph-ql.service';

interface SchemaState {
  dataAreas: DataArea[];
  isLoading: boolean;
  error: string | null;
}

const initialState: SchemaState = {
  dataAreas: [],
  isLoading: false,
  error: null,
};

export const SchemaStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withMethods((store, graphqlService = inject(GraphqlService)) => ({
    loadDataAreas: rxMethod<void>(
      pipe(
        switchMap(() => {
          patchState(store, { isLoading: true, error: null });
          return from(graphqlService.extractDataAreasFromSchema()).pipe(
            tap((dataAreas) => patchState(store, { dataAreas, isLoading: false })),
            catchError((err) => {
              console.error(err);
              patchState(store, { isLoading: false, error: 'Failed to load fields from API' });
              return EMPTY;
            }),
          );
        }),
      ),
    ),
  })),
  withHooks({
    onInit(store) {
      store.loadDataAreas(); // auto-loads when store is first injected
    },
  }),
);
