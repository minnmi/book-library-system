import {Injectable} from "@angular/core";
import {BaseService} from "../../shared/base.service";
import {HttpClient} from "@angular/common/http";
import {BookingRequest} from "./booking.request";
import {BookingResponse} from "./booking.response";

@Injectable({
    providedIn: "root"
})
export class BookingService extends BaseService<BookingRequest, BookingResponse> {
    public constructor(private http: HttpClient) {
        super("/bookings", http);
    }
}