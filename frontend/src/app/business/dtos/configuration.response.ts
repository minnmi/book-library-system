import {ConfigurationRequest} from "./configuration.request";

export class ConfigurationResponse extends ConfigurationRequest{
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
