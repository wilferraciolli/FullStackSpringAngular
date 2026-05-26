import { inject } from '@angular/core';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';

import { catchError, EMPTY, from, pipe, switchMap, tap } from 'rxjs';

import { DataArea, QueryPayload } from '../components/query-builder/query-builder';
import { GraphqlService } from './graph-ql.service';

interface SchemaState {
  dataAreas: DataArea[];
  isLoading: boolean;
  error: string | null;
  queryResults: any | null;
  isExecuting: boolean;
  queryError: string | null;
  // Pagination metadata returned by the last query
  totalElements: number;
  totalPages: number;
  currentPage: number;
  pageSize: number;
  // Stored so page-navigation can re-run without touching the query builder
  lastPayload: Omit<QueryPayload, 'page'> | null;
}

const initialState: SchemaState = {
  dataAreas: [],
  isLoading: false,
  error: null,
  queryResults: null,
  isExecuting: false,
  queryError: null,
  totalElements: 0,
  totalPages: 0,
  currentPage: 0,
  pageSize: 20,
  lastPayload: null,
};

function extractPaginationMeta(data: any): { totalElements: number; totalPages: number; currentPage: number } {
  // Response: { people: { content: [], totalElements: N, totalPages: N, page: N } }
  const firstArea = Object.values(data ?? {})[0] as any;
  return {
    totalElements: firstArea?.totalElements ?? 0,
    totalPages:    firstArea?.totalPages    ?? 0,
    currentPage:   firstArea?.page          ?? 0,
  };
}

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

    runQuery: rxMethod<QueryPayload>(
      pipe(
        switchMap((payload) => {
          const { page, pageSize, ...rest } = payload;
          patchState(store, {
            isExecuting: true, queryError: null, queryResults: null,
            lastPayload: { ...rest, pageSize },
            currentPage: page, pageSize,
          });
          return from(graphqlService.executeQuery(store.dataAreas(), payload)).pipe(
            tap((queryResults) => {
              const meta = extractPaginationMeta(queryResults);
              patchState(store, { queryResults, isExecuting: false, ...meta });
            }),
            catchError((err) => {
              patchState(store, { isExecuting: false, queryError: err.message ?? 'Query failed' });
              return EMPTY;
            }),
          );
        }),
      ),
    ),

    changePage: rxMethod<number>(
      pipe(
        switchMap((page) => {
          const last = store.lastPayload();
          if (!last) return EMPTY;
          patchState(store, { isExecuting: true, queryError: null, currentPage: page });
          const payload: QueryPayload = { ...last, page };
          return from(graphqlService.executeQuery(store.dataAreas(), payload)).pipe(
            tap((queryResults) => {
              const meta = extractPaginationMeta(queryResults);
              patchState(store, { queryResults, isExecuting: false, ...meta });
            }),
            catchError((err) => {
              patchState(store, { isExecuting: false, queryError: err.message ?? 'Query failed' });
              return EMPTY;
            }),
          );
        }),
      ),
    ),
  })),
  withHooks({
    onInit(store) {
      store.loadDataAreas();
    },
  }),
);
