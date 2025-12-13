import {ChangeDetectionStrategy, Component, inject, OnInit} from '@angular/core';
import {CommonModule, DatePipe} from '@angular/common';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatTreeModule} from '@angular/material/tree';
import {OrgNodeType} from '../org-node-type.constant';
import {OrgChartStore} from '../store/org-chart.store';
import {OrgTreeNode, OrgTreeNodeJob} from '../org-tree-node.interface';
import {Pannable} from '../../shared/directives/pannable.directive';
import {CanvasWrapper} from '../../shared/components/canvas-wrapper/canvas-wrapper';

@Component({
  selector: 'wt-org-chart',
  imports: [CommonModule, MatTreeModule, MatIconModule, MatButtonModule, DatePipe, Pannable, CanvasWrapper],
  templateUrl: './org-chart.html',
  styleUrl: './org-chart.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OrgChart implements OnInit {
  readonly store = inject(OrgChartStore);
  nodeType = OrgNodeType;
  treeData = this.store.treeData;

  ngOnInit() {
    this.store.loadOrgChart();
  }

  // REPLACEMENT FOR NESTED TREE CONTROL
  // 1. How to get children:
  protected childrenAccessor = (node: OrgTreeNode) => {
    if ('children' in node && node.children) {
      return node.children;
    }

    return [];
  }

  // 2. Predicate for "expandability" (replaces hasChild)
  protected hasChild = (_: number, node: OrgTreeNode) =>
    node.type !== OrgNodeType.JOB
    && 'children' in node
    && (node.children?.length ?? 0) > 0;

  // Type Guard Helper
  protected isJob(node: OrgTreeNode): node is OrgTreeNodeJob {
    return node.type === OrgNodeType.JOB;
  }

  protected readonly OrgNodeType = OrgNodeType;
}
