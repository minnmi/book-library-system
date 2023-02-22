export class ConfigurationRequest {
  private _maximumNumberBooksUser: number;
  private _maximumBookingPeriod: number;
  private _proportionBooksStock: number;

  public constructor() {
  }

  get maximumNumberBooksUser(): number {
    return this._maximumNumberBooksUser;
  }

  set maximumNumberBooksUser(value: number) {
    this._maximumNumberBooksUser = value;
  }

  get maximumBookingPeriod(): number {
    return this._maximumBookingPeriod;
  }

  set maximumBookingPeriod(value: number) {
    this._maximumBookingPeriod = value;
  }

  get proportionBooksStock(): number {
    return this._proportionBooksStock;
  }

  set proportionBooksStock(value: number) {
    this._proportionBooksStock = value;
  }
}
