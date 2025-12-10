import {Injectable} from '@angular/core';
import {forkJoin, Observable, of} from 'rxjs';
import {delay, map} from 'rxjs/operators';
import {OrgTreeNode, OrgTreeNodeJob, OrgTreeNodeUnit} from '../org-tree-node.interface';
import {Occupancy} from '../occupancy.interface';
import {OrgNodeType} from '../org-node-type.constant';

@Injectable({providedIn: 'root'})
export class OrgChartService {
  public getTree(): Observable<OrgTreeNode[]> {
    return forkJoin({
      orgs: this.fetchOrgs(),
      entities: this.fetchEntities(),
      depts: this.fetchDepartments(),
      jobs: this.fetchJobs(),
      occupancies: this.fetchOccupancies()
    }).pipe(
      map(data => this.buildHierarchy(
        data.orgs,
        data.entities,
        data.depts,
        data.jobs,
        data.occupancies
      ))
    );
  }

  private buildHierarchy(
    orgs: any[],
    entities: any[],
    depts: any[],
    jobs: any[],
    occupancies: any[]
  ): OrgTreeNode[] {

    // 1. Create a fast lookup map for all nodes
    const nodeMap = new Map<string, OrgTreeNode>();
    const rootNodes: OrgTreeNode[] = [];

    // 2. Helper to create Container Nodes (Org, Entity, Dept)
    const createContainer = (item: any, type: OrgNodeType): OrgTreeNodeUnit => ({
      id: item.id,
      title: item.title,
      type: type as any, // Type assertion for specific union member
      parentId: item.parent_id,
      children: [] // Initialized empty
    });

    // 3. Populate Map with Containers
    orgs.forEach(i => nodeMap.set(i.id, createContainer(i, OrgNodeType.ORG)));
    entities.forEach(i => nodeMap.set(i.id, createContainer(i, OrgNodeType.ORG_ENTITY)));
    depts.forEach(i => nodeMap.set(i.id, createContainer(i, OrgNodeType.DEPARTMENT)));

    // 4. Process Jobs (The Leaf Nodes)
    const now = new Date();

    jobs.forEach(job => {
      // Find the SINGLE active occupancy for this job
      const activeOcc = occupancies.find(o =>
        o.job_id === job.id &&
        new Date(o.start_date) <= now &&
        new Date(o.end_date) >= now
      );

      // Map to strict Occupancy interface
      const occupancyData: Occupancy | null = activeOcc ? {
        occupancyId: activeOcc.id,
        personId: activeOcc.person_id,
        personName: activeOcc.person_name,
        startDate: activeOcc.start_date,
        endDate: activeOcc.end_date
      } : null;

      const jobNode: OrgTreeNodeJob = {
        id: job.id,
        title: job.title,
        type: OrgNodeType.JOB,
        parentId: job.parent_id,
        // children: undefined, // Strict undefined
        occupancy: occupancyData
      };

      nodeMap.set(job.id, jobNode);
    });

    // 5. Stitch the Tree Together
    nodeMap.forEach(node => {
      if (node.parentId && nodeMap.has(node.parentId)) {
        const parent = nodeMap.get(node.parentId)!;

        // Validation: Only containers can have children
        if ('children' in parent) {

          // RULE: OrgEntity cannot have Jobs directly
          // if (node.type === OrgNodeType.JOB && parent.type === OrgNodeType.ORG_ENTITY) {
          //   console.warn(`[Data Integrity] Job '${node.title}' cannot report directly to Entity '${parent.title}'. Skipping.`);
          //   return;
          // }

          parent.children.push(node);
        } else {
          console.warn(`[Data Integrity] Node '${node.title}' trying to attach to Leaf '${parent.title}'`);
        }
      } else {
        // No parent found in map? It's a Root.
        rootNodes.push(node);
      }
    });

    return rootNodes;
  }


  // --- MOCK DATA SIMULATION (Database Tables) ---

  private fetchOrgs() {
    return of([
      {id: 'org-1', title: 'Global Corp HQ', parent_id: null}
    ]).pipe(delay(200));
  }

  private fetchEntities() {
    return of([
      {id: 'ent-1', title: 'North America Branch (Entity)', parent_id: 'org-1'},
      {id: 'ent-2', title: 'Europe Branch (Entity)', parent_id: 'org-1'}
    ]).pipe(delay(200));
  }

  private fetchDepartments() {
    return of([
      // Dept under Org directly
      {id: 'dept-1', title: 'Corporate HR', parent_id: 'org-1'},
      // Depts under Entities
      {id: 'dept-2', title: 'Engineering (NA)', parent_id: 'ent-1'},
      {id: 'dept-3', title: 'Sales (EU)', parent_id: 'ent-2'}
    ]).pipe(delay(300));
  }

  private fetchJobs() {
    return of([
      // Jobs under Corporate HR
      {id: 'job-1', title: 'HR Director', parent_id: 'dept-1'},
      {id: 'job-2', title: 'HR Intern', parent_id: 'dept-1'},

      // Jobs under Engineering
      {id: 'job-3', title: 'Lead Developer', parent_id: 'dept-2'},
      {id: 'job-4', title: 'QA Engineer', parent_id: 'dept-2'}, // Vacant

      // Jobs under Sales
      {id: 'job-5', title: 'Sales Manager', parent_id: 'dept-3'},
    ]).pipe(delay(300));
  }

  private fetchOccupancies() {
    return of([
      // Current Occupants
      {
        id: 'occ-1', job_id: 'job-1', person_id: 'p-1', person_name: 'Alice Johnson',
        start_date: '2023-01-01', end_date: '2099-01-01'
      },
      {
        id: 'occ-2', job_id: 'job-3', person_id: 'p-2', person_name: 'Bob Smith',
        start_date: '2022-05-15', end_date: '2099-01-01'
      },
      {
        id: 'occ-3', job_id: 'job-5', person_id: 'p-3', person_name: 'Charlie Brown',
        start_date: '2021-08-20', end_date: '2099-01-01'
      },
      // Old Occupant (Should NOT appear)
      {
        id: 'occ-old', job_id: 'job-1', person_id: 'p-99', person_name: 'Old Manager',
        start_date: '2010-01-01', end_date: '2022-12-31'
      }
    ]).pipe(delay(400));
  }
}
