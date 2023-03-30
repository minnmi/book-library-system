import {AuthorResponse} from "../author/author.response";
import {PublisherResponse} from "../publisher/publisher.response";
import {LiteratureCategoryResponse} from "../literature-category/literature-category.response";

export class BookResponse {
  public id: number;
  public name: string;
  public isbn: string;
  public authors: AuthorResponse[];
  public publisher: PublisherResponse[];
  public quantity: number;
  public categoryName: LiteratureCategoryResponse;
}
