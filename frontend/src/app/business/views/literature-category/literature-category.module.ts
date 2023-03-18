import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LiteratureCategoryRoutingModule } from './literature-category-routing.module';
import { LiteratureCategoryComponent } from './literature-category.component';
import {ButtonModule, TableModule} from "@coreui/angular";


@NgModule({
  declarations: [
    LiteratureCategoryComponent
  ],
    imports: [
        CommonModule,
        LiteratureCategoryRoutingModule,
        ButtonModule,
        TableModule
    ]
})
export class LiteratureCategoryModule { }
