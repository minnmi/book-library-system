import {BookingRequest} from "./booking.request";

export class BookingResponse extends BookingRequest{
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
