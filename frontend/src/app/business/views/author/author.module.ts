import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuthorRoutingModule} from './author-routing.module';
import {AuthorComponent} from './author.component';
import {TableModule} from "@coreui/angular";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";


@NgModule({
  declarations: [
    AuthorComponent
  ],
  imports: [
    CommonModule,
    BrowserAnimationsModule,
    AuthorRoutingModule,
    TableModule,
  ]
})
export class AuthorModule { }
