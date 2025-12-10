import {Injectable} from '@angular/core';
import { Observable, forkJoin, of } from 'rxjs';
import { delay, map } from 'rxjs/operators';
import {OrgTreeNode, OrgTreeNodeJob, OrgTreeNodeUnit} from '../org-tree-node.interface';
import {Occupancy} from '../occupancy.interface';
import {OrgNodeType} from '../org-node-type.constant';

@Injectable()
export class OrgChartBuilderService {

// public  buildHierarchy(
//   orgs: any[],
//   entities: any[],
//   depts: any[],
//   jobs: any[],
//   occupancies: any[] // Flat list of active occupancies
// ): OrgNodeType[] {
//
//   const nodeMap = new Map<string, OrgNodeType>();
//
//   // Helper for Containers
//   const createContainer = (item: any, type: OrgNodeType): OrgTreeNodeUnit => ({
//     id: item.id,
//     title: item.title,
//     type: type as any,
//     parentId: item.parent_id,
//     children: []
//   });
//
//   // 1. Initialize Containers
//   orgs.forEach(i => nodeMap.set(i.id, createContainer(i, OrgNodeType.ORG)));
//   entities.forEach(i => nodeMap.set(i.id, createContainer(i, OrgNodeType.ORG_ENTITY)));
//   depts.forEach(i => nodeMap.set(i.id, createContainer(i, OrgNodeType.DEPARTMENT)));
//
//   // 2. Process Jobs (The Split Interface)
//   jobs.forEach(j => {
//     // Find ACTIVE occupancy for this job
//     // Assuming 1 active occupancy per job based on dates
//     const activeOcc = occupancies.find(o =>
//       o.job_id === j.id &&
//       new Date(o.start_date) <= new Date() &&
//       new Date(o.end_date) >= new Date()
//     );
//
//     const occupancyData: Occupancy | null = activeOcc ? {
//       occupancyId: activeOcc.id,
//       personId: activeOcc.person_id,
//       personName: activeOcc.person_name,
//       startDate: activeOcc.start_date,
//       endDate: activeOcc.end_date
//     } : null;
//
//     const jobNode: OrgTreeNode = {
//       id: j.id,
//       title: j.title,
//       type: OrgNodeType.JOB,
//       parentId: j.parent_id,
//       // children: undefined, // Explicitly undefined
//       occupancy: occupancyData // Single object or null
//     };
//
//     nodeMap.set(j.id, jobNode);
//   });
//
//   // 3. Construct Tree
//   const rootNodes: OrgTreeNode[] = [];
//
//   nodeMap.forEach(node => {
//     if (node.parentId && nodeMap.has(node.parentId)) {
//       const parent = nodeMap.get(node.parentId)!;
//
//       // Safety Check: Only add to parents that have a 'children' array
//       if ('children' in parent) {
//
//         // BUSINESS RULE: OrgEntity cannot have Jobs
//         if (node.type === OrgNodeType.JOB && parent.type === OrgNodeType.ORG_ENTITY) {
//           console.warn(`Skipping Job ${node.title} assigned to Entity`);
//           return;
//         }
//
//         parent.children.push(node);
//       }
//     } else {
//       rootNodes.push(node);
//     }
//   });
//
//   return rootNodes;
}
