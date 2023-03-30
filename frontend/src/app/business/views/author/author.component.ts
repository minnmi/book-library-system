import { Component, OnInit } from '@angular/core';
import {AuthorService} from "./author.service";
import {Page, Pageable} from "../../shared/base.service";
import {AuthorResponse} from "./author.response";
import {FormBuilder, FormGroup} from "@angular/forms";
import {AuthorRequest} from "./author.request";
import {Observable} from "rxjs";
import {ToasterService} from "@coreui/angular";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent implements OnInit {

 public authors: Page<AuthorResponse> = new Page<AuthorResponse>();
 foo = [];
    visible = false;
    updating = false;
    form: FormGroup;
  constructor(private authorService: AuthorService, private fb: FormBuilder, private toastr: ToastrService) { }

  ngOnInit(): void {
      this.fetch();

      this.form = this.fb.group({
          id: [],
          name: [],
      })
  }

  fetch() {
      this.authorService.list(new Pageable()).subscribe(page => {
          this.authors = page;
      });
  }
    add() {
        this.updating = false;
        this.visible = !this.visible;
    }

    update(id: number) {
        this.updating = true
        this.visible = !this.visible;
        this.form.patchValue({"id": id});
    }

    getModalTitle(updating) {
       if (updating)
           return "Editando autor";
       else
           return "Adicionar autor";
    }


    handleLiveDemoChange($event: boolean) {
        this.visible = $event;
    }

    close() {
        this.visible = false;
    }

    save($event: SubmitEvent) {
        let author = new AuthorRequest();
        author.name = this.form.get("name").value;
        let $observable: Observable<AuthorResponse> = null;
        if (this.updating) {
            $observable = this.authorService.put(this.form.get("id").value, author);
        } else {
            $observable = this.authorService.post(author);
        }

        $observable.subscribe(value => {
            this.toastr.success(this.updating ? "Atualizado" : "Criado");
            this.fetch();
            this.close();
        });
    }
}
