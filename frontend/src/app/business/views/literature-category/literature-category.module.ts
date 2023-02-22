import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LiteratureCategoryRoutingModule } from './literature-category-routing.module';
import { LiteratureCategoryComponent } from './literature-category.component';


@NgModule({
  declarations: [
    LiteratureCategoryComponent
  ],
  imports: [
    CommonModule,
    LiteratureCategoryRoutingModule
  ]
})
export class LiteratureCategoryModule { }
