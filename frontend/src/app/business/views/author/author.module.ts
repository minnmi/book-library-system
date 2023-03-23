import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {AuthorRoutingModule} from './author-routing.module';
import {AuthorComponent} from './author.component';
import {ButtonModule, FormModule, ModalModule, TableModule} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";
import {AuthorService} from "./author.service";
import {HttpClientModule} from "@angular/common/http";
import { FormComponent } from './form/form.component';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    AuthorComponent,
    FormComponent
  ],
    imports: [
        CommonModule,
        AuthorRoutingModule,
        TableModule,
        ButtonModule,
        IconModule,
        HttpClientModule,
        ModalModule,
        FormModule,
        ReactiveFormsModule
    ],
  providers: [
      AuthorService
  ]
})
export class AuthorModule { }
