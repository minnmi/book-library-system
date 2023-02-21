
export class AuthorRequest {
  private _name: string;

  public constructor() {
    this._name = null;
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }
}
