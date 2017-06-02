import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../shared';
import { SchoolNetAdminModule } from '../admin/admin.module';
import {
    TeacherScheduleComponent,
    teacherScheduleRoute
} from './';

const ENTITY_STATES = [
    ...teacherScheduleRoute
];

import { TeacherScheduleService } from './teacher-schedule.service';

@NgModule({
    imports: [
        SchoolNetSharedModule,
        SchoolNetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TeacherScheduleComponent
    ],
    entryComponents: [
        TeacherScheduleComponent
    ],
    providers: [TeacherScheduleService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetTeacherScheduleModule {}
