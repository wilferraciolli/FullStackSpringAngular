import { IdValue } from './id-value';

/**
 * Default meta data interface to be extended by other interfaces;
 */
export interface MetaData {

  id: MetaData;
}

export interface MetaDataRules {
  hidden?: string;
  readonly?: string;
  mandatory?: string;
}

export interface MetaDataWithValues {
  hidden?: string;
  readonly?: string;
  mandatory?: string;
  values: Array<IdValue>;
}

