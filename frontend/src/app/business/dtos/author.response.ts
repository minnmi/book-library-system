import {AuthorRequest} from "./author.request";

export class AuthorResponse extends AuthorRequest{
  private _id: number;

  public constructor() {
    super()
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }
}
