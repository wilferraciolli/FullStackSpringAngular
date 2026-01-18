import {OrgNodeType} from '../org-node-type.constant';

export interface StructureFormBody {
  id: string | null;
  name: string;
  structureType: OrgNodeType;
  startDate: string;
  endDate: string | null;
}
