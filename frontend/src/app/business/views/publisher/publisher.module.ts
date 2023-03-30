import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PublisherRoutingModule } from './publisher-routing.module';
import { PublisherComponent } from './publisher.component';
import {ButtonModule, TableModule} from "@coreui/angular";
import {IconModule} from "@coreui/icons-angular";


@NgModule({
  declarations: [
    PublisherComponent
  ],
    imports: [
        CommonModule,
        PublisherRoutingModule,
        ButtonModule,
        TableModule,
        IconModule
    ]
})
export class PublisherModule { }
