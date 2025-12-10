import {OrgTreeNode} from '../org-tree-node.interface';
import {patchState, signalStore, withMethods, withState} from '@ngrx/signals';
import {inject} from '@angular/core';
import {OrgChartService} from '../services/org-chart.service';
import {rxMethod} from '@ngrx/signals/rxjs-interop';
import {pipe, switchMap, tap} from 'rxjs';

type OrgChartState = {
  treeData: OrgTreeNode[]; // Uses the Union Type
  isLoading: boolean;
};

const initialState: OrgChartState = {
  treeData: [],
  isLoading: false,
};

export const OrgChartStore = signalStore(
  {providedIn: 'root'},
  withState(initialState),
  withMethods((store, orgChartService = inject(OrgChartService)) => ({
    loadOrgChart: rxMethod<void>(
      pipe(
        tap(() => patchState(store, {isLoading: true})),
        switchMap(() => orgChartService.getTree().pipe(
          tap((data) => patchState(store, {
            treeData: data,
            isLoading: false
          }))
        ))
      )
    )
  }))
)
