import {OrgNodeType} from './org-node-type.constant';
import {Occupancy} from './occupancy.interface';

interface OrgTreeNodeBase {
  id: string; // uuid
  parentId: string | null; // uuid
  title: string;
  type: OrgNodeType;
}

export interface OrgTreeNodeUnit extends OrgTreeNodeBase {
  type: OrgNodeType.ORGANISATION | OrgNodeType.ORGANISATION_ENTITY | OrgNodeType.DEPARTMENT;
  children: OrgTreeNode[]; // Recursive children
}

export interface OrgTreeNodeJob extends OrgTreeNodeBase {
  type: OrgNodeType.JOB;
  occupancy: Occupancy | null
}

export type OrgTreeNode = OrgTreeNodeUnit | OrgTreeNodeJob;
