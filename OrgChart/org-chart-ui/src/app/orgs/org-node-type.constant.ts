export enum OrgNodeType {
  ORG = 'ORG', // only 1, no jobs here
  ORG_ENTITY = 'ORG_ENTITY', // child of either ORG or Department, no jobs here
  DEPARTMENT = 'ORG_ENTITY', // can have 1 reporting job or many child jobs
  JOB = 'JOB', // may be vacant or will have an occupancy (id + personId + dateRange)
}
