import {AuthorResponse} from "./author.response";
import {BookingRequest} from "./booking.request";

export class BookResponse extends BookingRequest{
  private _id: number;

  public constructor() {
    super();
  }

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }
}
