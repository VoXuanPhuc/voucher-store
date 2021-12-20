import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SlideDetailComponent } from './slide-detail/slide-detail.component';
import { SlideComponent } from './slide/slide.component';

const routes: Routes = [
    {
        path: 'slide',
        component: SlideDetailComponent,
        // canActivate: [UserRouteAccessService],
    },
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class OtherRoutingModule {}
