export class PublisherResponse {
  private _id: number;
  private _name: string;

  public constructor() {
  }

  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }
}
