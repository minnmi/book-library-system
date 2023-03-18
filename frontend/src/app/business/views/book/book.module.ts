import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookRoutingModule } from './book-routing.module';
import { BookComponent } from './book.component';
import {ButtonModule, TableModule} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";


@NgModule({
  declarations: [
    BookComponent
  ],
    imports: [
        CommonModule,
        BookRoutingModule,
        ButtonModule,
        TableModule,
        IconModule
    ]
})
export class BookModule { }
