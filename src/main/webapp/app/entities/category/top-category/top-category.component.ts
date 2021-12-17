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
    categories: ICategory[];
    constructor(protected categoryService: CategoryService) {
        this.categories = [];
    }

    ngOnInit(): void {
        this.loadHotCategory();
    }

    loadHotCategory(): void {
        this.categoryService.query().subscribe((res: HttpResponse<ICategory[]>) => {
            this.categories = res.body ?? [];
            this.categories = this.categories.slice(0, 8);
        });
    }

    trackId(index: number, item: ICategory): number {
        return item.id!;
    }
}
