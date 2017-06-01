import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../../shared';
import {SchoolNetAdminModule} from "../../admin/admin.module";
import {
    TeacherManagementService,
    TeacherManagementDialogComponent,
    TeacherManagementPopupService,
    TeacherManagementComponent,
    TeacherManagementPopupComponent,
    TEACHER_ROUTE,
    TEACHER_POPUP_ROUTE,
} from './';


const ENTITY_STATES = [
    ...TEACHER_ROUTE,
    ...TEACHER_POPUP_ROUTE,
];

@NgModule({
    imports: [
        SchoolNetSharedModule,
        SchoolNetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TeacherManagementComponent,
        TeacherManagementDialogComponent,
        TeacherManagementPopupComponent,
    ],
    entryComponents: [
        TeacherManagementComponent,
        TeacherManagementDialogComponent,
        TeacherManagementPopupComponent,
    ],
    providers: [
        TeacherManagementService,
        TeacherManagementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetTeacherManagementModule {}
