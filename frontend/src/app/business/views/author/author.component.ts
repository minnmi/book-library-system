import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent implements OnInit {

  public authors = [
    {
      id: 1,
      name: 'Fulano',
    },
    {
      id: 2,
      name: 'Ciclano',
    },
    {
      id: 3,
      name: 'Beutrano',
    }
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
