import {AuthorResponse} from "./author.response";
import {OnlyId} from "./OnlyId";

export class BookRequest {
  private _name: string;
  private _isbn: string;
  private _quantity: number;
  private _authors: OnlyId[];
  private _publisher: OnlyId;
  private _literatureCategory: OnlyId;

  public constructor() {
  }

  get name(): string {
    return this._name;
  }

  set name(value: string) {
    this._name = value;
  }

  get isbn(): string {
    return this._isbn;
  }

  set isbn(value: string) {
    this._isbn = value;
  }

  get quantity(): number {
    return this._quantity;
  }

  set quantity(value: number) {
    this._quantity = value;
  }

  get authorsId(): number[] {
    return this._authors.map(u => u.id);
  }

  set authorsIds(authorsId: number[]) {
    this._authors = authorsId.map(id => new OnlyId(id));
  }

  get publisherId(): number {
    return this._publisher.id;
  }

  set publisherId(value: number) {
    this._publisher = new OnlyId(value);
  }

  get literatureCategoryId(): number {
    return this._literatureCategory.id;
  }

  set literatureCategoryId(value: number) {
    this._literatureCategory = new OnlyId(value);
  }
}
