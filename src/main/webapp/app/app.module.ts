import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { SchoolNetSharedModule, UserRouteAccessService } from './shared';
import { SchoolNetHomeModule } from './home/home.module';
import { SchoolNetAdminModule } from './admin/admin.module';
import { SchoolNetAccountModule } from './account/account.module';
import { SchoolNetEntityModule } from './entities/entity.module';
import { SchoolNetHeadTeacherModule } from './headteacher/headteacher.module';
import { SchoolNetPupilHomeModule } from './pupil-home/pupil-home.module';
import { SchoolNetTeacherHomeModule } from './teacher-home/teacher-home.module';
import { SchoolNetTeacherScheduleModule } from './teacher-schedule/teacher-schedule.module';

import { LayoutRoutingModule } from './layouts';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        SchoolNetSharedModule,
        SchoolNetHomeModule,
        SchoolNetAdminModule,
        SchoolNetAccountModule,
        SchoolNetEntityModule,
        SchoolNetHeadTeacherModule,
        SchoolNetPupilHomeModule,
        SchoolNetTeacherHomeModule,
        SchoolNetTeacherScheduleModule,
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent,
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class SchoolNetAppModule {}
