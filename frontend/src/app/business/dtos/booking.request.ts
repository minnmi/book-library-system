import {UserResponse} from "./user.response";
import {OnlyId} from "./OnlyId";

export class BookingRequest {
  private _user: OnlyId;
  private _book: OnlyId;
  private _currentDate: Date;
  private _priority: Date;

  public constructor() {

  }

  get userId(): number {
    return this._user.id;
  }

  set user(userId: number) {
    this._user = new OnlyId(userId);
  }

  get bookId(): number {
    return this._book.id;
  }

  set book(bookId: number) {
    this._book = new OnlyId(bookId);
  }

  get currentDate(): Date {
    return this._currentDate;
  }

  set currentDate(value: Date) {
    this._currentDate = value;
  }

  get priority(): Date {
    return this._priority;
  }

  set priority(value: Date) {
    this._priority = value;
  }
}
