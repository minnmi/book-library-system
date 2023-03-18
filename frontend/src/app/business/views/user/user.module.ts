import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import {ButtonModule, TableModule} from "@coreui/angular";


@NgModule({
  declarations: [
    UserComponent
  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        ButtonModule,
        TableModule
    ]
})
export class UserModule { }
