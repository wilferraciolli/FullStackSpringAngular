export interface Message {
  id: string;
  clientId: string;
  feature: string;
  subFeature: string;
  resourceId: string;
  message: string;
  timestamp: Date;
}
