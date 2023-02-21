import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoanedRoutingModule } from './loaned-routing.module';
import { LoanedComponent } from './loaned.component';


@NgModule({
  declarations: [
    LoanedComponent
  ],
  imports: [
    CommonModule,
    LoanedRoutingModule
  ]
})
export class LoanedModule { }
