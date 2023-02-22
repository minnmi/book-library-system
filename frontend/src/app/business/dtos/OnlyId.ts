export class OnlyId {
  private _id: number;

  public constructor(id: number) {
    this._id = id;
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }
}
