import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LiteratureCategoryComponent} from "./literature-category.component";

const routes: Routes = [{
  path: '',
  component: LiteratureCategoryComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LiteratureCategoryRoutingModule { }
