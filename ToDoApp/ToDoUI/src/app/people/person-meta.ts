import { MetaData, MetaDataRules, MetaDataWithValues } from '../shared/response/meta-data';

export interface PersonMeta extends MetaData {

  firstName: MetaDataRules;
  lastName: MetaDataRules;
  genderId: MetaDataWithValues;
  maritalStatusId: MetaDataWithValues;
}
