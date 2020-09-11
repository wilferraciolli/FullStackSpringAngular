import { MetaData, MetaDataRules, MetaDataWithValues } from '../shared/response/meta-data';

export interface TodoMeta extends MetaData {

  baseUrl: MetaDataRules;
  name: MetaDataRules;
}
