import {AuthorResponse} from "../author/author.response";
import {OnlyId} from "../../shared/only-id";

export class BookRequest {
  public name: string;
  public isbn: string;
  public authors: OnlyId[];
  public publisher: OnlyId;
  public quantity: number;
  public categoryName: OnlyId;
}
