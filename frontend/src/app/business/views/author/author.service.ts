import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {AuthorRequest} from "./author.request";
import {AuthorResponse} from "./author.response";
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: "root"
})
export class AuthorService extends BaseService<AuthorRequest, AuthorResponse> {
    constructor(public http: HttpClient) {
        super("/authors", http);
    }
}