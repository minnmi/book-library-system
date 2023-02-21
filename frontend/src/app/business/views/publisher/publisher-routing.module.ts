import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {PublisherComponent} from "./publisher.component";

const routes: Routes = [{
  path: '',
  component: PublisherComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublisherRoutingModule { }
