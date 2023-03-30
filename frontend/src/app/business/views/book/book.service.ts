import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {HttpClient} from "@angular/common/http";
import {BookRequest} from "./book.request";
import {BookResponse} from "./book.response";

@Injectable({
    providedIn: "root"
})
export class BookService extends BaseService<BookRequest, BookResponse> {
    public constructor(private http: HttpClient) {
        super("/books", http);
    }
}