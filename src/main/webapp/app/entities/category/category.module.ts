import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CategoryComponent } from './list/category.component';
import { CategoryDetailComponent } from './detail/category-detail.component';
import { CategoryUpdateComponent } from './update/category-update.component';
import { CategoryDeleteDialogComponent } from './delete/category-delete-dialog.component';
import { CategoryRoutingModule } from './route/category-routing.module';
import { TopCategoryComponent } from './top-category/top-category.component';

@NgModule({
  imports: [SharedModule, CategoryRoutingModule],
  declarations: [CategoryComponent, CategoryDetailComponent, CategoryUpdateComponent, CategoryDeleteDialogComponent, TopCategoryComponent],
  entryComponents: [CategoryDeleteDialogComponent],
  exports: [TopCategoryComponent],
})
export class CategoryModule {}
