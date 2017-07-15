import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../../shared';
import {
    AttendancesLogService,
    AttendancesLogPopupService,
    AttendancesLogComponent,
    AttendancesLogDetailComponent,
    AttendancesLogDialogComponent,
    AttendancesLogPopupComponent,
    AttendancesLogDeletePopupComponent,
    AttendancesLogDeleteDialogComponent,
    attendancesLogRoute,
    attendancesLogPopupRoute,
} from './';

const ENTITY_STATES = [
    ...attendancesLogRoute,
    ...attendancesLogPopupRoute,
];

@NgModule({
    imports: [
        SchoolNetSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AttendancesLogComponent,
        AttendancesLogDetailComponent,
        AttendancesLogDialogComponent,
        AttendancesLogDeleteDialogComponent,
        AttendancesLogPopupComponent,
        AttendancesLogDeletePopupComponent,
    ],
    entryComponents: [
        AttendancesLogComponent,
        AttendancesLogDialogComponent,
        AttendancesLogPopupComponent,
        AttendancesLogDeleteDialogComponent,
        AttendancesLogDeletePopupComponent,
    ],
    providers: [
        AttendancesLogService,
        AttendancesLogPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetAttendancesLogModule {}
