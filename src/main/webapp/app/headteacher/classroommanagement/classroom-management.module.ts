import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../../shared';
import {SchoolNetAdminModule} from "../../admin/admin.module";
import {
    ClassroomManagementService,
    ClassroomManagementPopupService,
    ClassroomManagementComponent,
    ClassroomManagementDialogComponent,
    ClassroomManagementPopupComponent,
    classroomRoute,
    classroomPopupRoute,
} from './';

const ENTITY_STATES = [
    ...classroomRoute,
    ...classroomPopupRoute,
];

@NgModule({
    imports: [
        SchoolNetSharedModule,
        SchoolNetAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClassroomManagementComponent,
        ClassroomManagementDialogComponent,
        ClassroomManagementPopupComponent,
    ],
    entryComponents: [
        ClassroomManagementComponent,
        ClassroomManagementDialogComponent,
        ClassroomManagementPopupComponent,
    ],
    providers: [
        ClassroomManagementService,
        ClassroomManagementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetClassroomManagementModule {}
