export class LiteratureCategoryRequest {
  private _categoryName: string;

  public constructor() {
  }

  get categoryName(): string {
    return this._categoryName;
  }

  set categoryName(value: string) {
    this._categoryName = value;
  }
}
