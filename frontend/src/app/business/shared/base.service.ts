import {catchError, Observable} from "rxjs";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";

export class Pageable {
    page: number;
    size: number;

    public toHttpParams(): HttpParams {
        let params = new HttpParams();

        if (this.page)
            params = params.append("page", this.page?.toString());

        if (this.size)
            params = params.append("size", this.size?.toString());

        return params;
    }
}

export class Page<T> {
    content: T[];
    number: number;
    totalElements: number;
}

export class BaseService<ReqT, ResT> {
    protected _url = null;
    private _http: HttpClient;
    private _api: string;
    protected constructor(api: string, http: HttpClient) {
        this._api = api;
        this._http = http;
        this._url = `${environment.apiUrl}${this._api}`;
    }

    public post(request: ReqT): Observable<ResT> {
        return this._http.post<ResT>(this._url + "/insert", request, {withCredentials: true});
    }

    public put(id: number, request: ReqT): Observable<ResT> {
        return this._http.put<ResT>(`${this._url}/${id}`, request);
    }

    public get(id: number): Observable<ResT> {
        return this._http.get<ResT>(`${this._url}/${id}`);
    }

    public delete(id: number): Observable<ResT> {
        return this._http.delete<ResT>(`${this._url}/${id}`);
    }

    public list(filters: Pageable): Observable<Page<ResT>> {
        return this._http.get<Page<ResT>>(this._url + "/find/all", {
            params: filters.toHttpParams(), withCredentials: true});
    }
}