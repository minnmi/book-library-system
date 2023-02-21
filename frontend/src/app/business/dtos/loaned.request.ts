import {OnlyId} from "./OnlyId";

export class LoanedRequest {
  private _id: number;

  private _initialDate: Date;

  private _finalDate: Date;

  private _returned: number;
  private _returnedDate: Date;
  private _user: OnlyId;
  private _book: OnlyId;

  public constructor() {
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  get initialDate(): Date {
    return this._initialDate;
  }

  set initialDate(value: Date) {
    this._initialDate = value;
  }

  get finalDate(): Date {
    return this._finalDate;
  }

  set finalDate(value: Date) {
    this._finalDate = value;
  }

  get returned(): number {
    return this._returned;
  }

  set returned(value: number) {
    this._returned = value;
  }

  get returnedDate(): Date {
    return this._returnedDate;
  }

  set returnedDate(value: Date) {
    this._returnedDate = value;
  }

  get user(): OnlyId {
    return this._user;
  }

  set user(value: OnlyId) {
    this._user = value;
  }

  get book(): OnlyId {
    return this._book;
  }

  set book(value: OnlyId) {
    this._book = value;
  }
}
