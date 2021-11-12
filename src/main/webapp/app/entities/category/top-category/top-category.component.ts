import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ICategory } from '../category.model';
import { CategoryService } from '../service/category.service';

@Component({
  selector: 'jhi-top-category',
  templateUrl: './top-category.component.html',
  styleUrls: ['./top-category.component.scss'],
})
export class TopCategoryComponent implements OnInit {
  categorys?: ICategory[];
  constructor(protected categoryService: CategoryService) {}

  ngOnInit(): void {
    this.loadHotCategory();
  }

  loadHotCategory(): void {
    this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => {
      this.categorys = res.body ?? [];
    });
  }
}
