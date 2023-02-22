import {OnlyId} from "./OnlyId";
import {UserResponse} from "./user.response";
import {BookResponse} from "./book.response";

export class LoanedResponse {
  private _id: number;
  private _initialDate: Date;
  private _finalDate: Date;
  private _returned: number;
  private _returnedDate: Date;
  private _user: UserResponse;
  private _book: BookResponse;


  constructor(id: number, initialDate: Date, finalDate: Date, returned: number, returnedDate: Date, user: UserResponse, book: BookResponse) {
    this._id = id;
    this._initialDate = initialDate;
    this._finalDate = finalDate;
    this._returned = returned;
    this._returnedDate = returnedDate;
    this._user = user;
    this._book = book;
  }

  get id(): number {
    return this._id;
  }

  get initialDate(): Date {
    return this._initialDate;
  }

  get finalDate(): Date {
    return this._finalDate;
  }

  get returned(): number {
    return this._returned;
  }

  get returnedDate(): Date {
    return this._returnedDate;
  }

  get user(): UserResponse {
    return this._user;
  }

  get book(): BookResponse {
    return this._book;
  }
}
