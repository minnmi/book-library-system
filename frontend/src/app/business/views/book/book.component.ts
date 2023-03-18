import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent implements OnInit {
  books = [{
    id: 1,
    nome: "Foo Bar",
    isbn: "123345",
    autores: "Matheus",
    editora: "Loka",
    quantidade: 123,
    categorias: "Terror",
  }];

  constructor() { }

  ngOnInit(): void {
  }

}
