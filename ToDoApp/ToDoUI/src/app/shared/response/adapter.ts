// interface to implement the model adapter pattern. it defines the object to be modeled.
export interface Adapter<T> {
  adapt(item: any, links: any, meta?: any): any;
}

