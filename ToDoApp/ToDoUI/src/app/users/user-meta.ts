import { MetaData, MetaDataRules, MetaDataWithValues } from '../shared/response/meta-data';

export interface UserMeta extends MetaData {

  firstName: MetaDataRules;
  lastName: MetaDataRules;
  username: MetaDataRules;
  password: MetaDataRules;
  roleIds: MetaDataWithValues;
}
