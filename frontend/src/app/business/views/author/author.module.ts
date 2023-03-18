import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuthorRoutingModule} from './author-routing.module';
import {AuthorComponent} from './author.component';
import {ButtonModule, TableModule} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";


@NgModule({
  declarations: [
    AuthorComponent
  ],
  imports: [
    CommonModule,
    AuthorRoutingModule,
    TableModule,
    ButtonModule,
      IconModule
  ]
})
export class AuthorModule { }
