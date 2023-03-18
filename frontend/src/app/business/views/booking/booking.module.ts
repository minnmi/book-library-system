import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookingRoutingModule } from './booking-routing.module';
import { BookingComponent } from './booking.component';
import {ButtonModule, TableModule} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";


@NgModule({
  declarations: [
    BookingComponent
  ],
    imports: [
        CommonModule,
        BookingRoutingModule,
        ButtonModule,
        TableModule,
        IconModule
    ]
})
export class BookingModule { }
