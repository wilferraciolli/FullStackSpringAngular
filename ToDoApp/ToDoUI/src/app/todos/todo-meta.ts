import {MetaData, MetaDataRules, MetaDataWithValues} from '../shared/response/meta-data';

export interface TodoMeta extends MetaData {

  typeId: MetaDataWithValues;
  name: MetaDataRules;
  createdDateTime: MetaDataRules;
  stateId: MetaDataWithValues;
  completionStats: MetaDataRules;
  published: MetaDataRules;
}
