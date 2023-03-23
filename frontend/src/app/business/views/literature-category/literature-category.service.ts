import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {HttpClient} from "@angular/common/http";
import {LiteratureCategoryRequest} from "./literature-category.request";
import {LiteratureCategoryResponse} from "./literature-category.response";

@Injectable({
    providedIn: "root"
})
export class LiteratureCategoryService extends BaseService<LiteratureCategoryRequest, LiteratureCategoryResponse> {
    public constructor(private http: HttpClient) {
        super("/literature-category", http);
    }
}