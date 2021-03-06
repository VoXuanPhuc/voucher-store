import { registerLocaleData } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import locale from '@angular/common/locales/en';
import localeVi from '@angular/common/locales/vi';
import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule, Title } from '@angular/platform-browser';
import { ServiceWorkerModule } from '@angular/service-worker';
import { FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { NgbDateAdapter, NgbDatepickerConfig } from '@ng-bootstrap/ng-bootstrap';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { httpInterceptorProviders } from 'app/core/interceptor/index';
import { SharedModule } from 'app/shared/shared.module';
import * as dayjs from 'dayjs';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { AccountModule } from './account/account.module';
import { AppRoutingModule } from './app-routing.module';
import { SERVER_API_URL } from './app.constants';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { NgbDateDayjsAdapter } from './config/datepicker-adapter';
import './config/dayjs';
import { fontAwesomeIcons } from './config/font-awesome-icons';
import { DateFormatPipe } from './entities/date-format.pipe';
// import { HomeModule } from './home/home.module';
import { EntityRoutingModule } from './entities/entity-routing.module';
import { ErrorComponent } from './layouts/error/error.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { MainComponent } from './layouts/main/main.component';
import { MyHeaderComponent } from './layouts/my-header/my-header.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { TemplateModule } from './template/template.module';

registerLocaleData(localeVi, 'vi-VN');

@NgModule({
    imports: [
        BrowserModule,
        SharedModule,
        // HomeModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
        // ServiceTypeModule,
        EntityRoutingModule,
        TemplateModule,
        AccountModule,
        AppRoutingModule,

        // TemplateRoutingModule,
        // Set this to true to enable service worker (PWA)
        ServiceWorkerModule.register('ngsw-worker.js', { enabled: false }),
        HttpClientModule,
        NgxWebstorageModule.forRoot({ prefix: 'jhi', separator: '-', caseSensitive: true }),
    ],
    providers: [
        Title,
        { provide: LOCALE_ID, useValue: 'en' },
        { provide: NgbDateAdapter, useClass: NgbDateDayjsAdapter },
        httpInterceptorProviders,
    ],
    declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent, MyHeaderComponent],
    bootstrap: [MainComponent],
})
export class AppModule {
    constructor(applicationConfigService: ApplicationConfigService, iconLibrary: FaIconLibrary, dpConfig: NgbDatepickerConfig) {
        applicationConfigService.setEndpointPrefix(SERVER_API_URL);
        registerLocaleData(locale);
        iconLibrary.addIcons(...fontAwesomeIcons);
        dpConfig.minDate = { year: dayjs().subtract(100, 'year').year(), month: 1, day: 1 };
    }
}
