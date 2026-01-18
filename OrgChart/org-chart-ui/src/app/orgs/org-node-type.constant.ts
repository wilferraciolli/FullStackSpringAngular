export enum OrgNodeType {
  ORGANISATION = 'ORGANISATION', // only 1, no jobs here
  ORGANISATION_ENTITY = 'ORGANISATION_ENTITY', // child of either ORG or Department, no jobs here
  DEPARTMENT = 'DEPARTMENT', // can have 1 reporting job or many child jobs
  JOB = 'JOB', // may be vacant or will have an occupancy (id + personId + dateRange)
}
