import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublisherRoutingModule } from './publisher-routing.module';
import { PublisherComponent } from './publisher.component';


@NgModule({
  declarations: [
    PublisherComponent
  ],
  imports: [
    CommonModule,
    PublisherRoutingModule
  ]
})
export class PublisherModule { }
