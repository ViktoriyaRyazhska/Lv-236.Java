import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../../shared';
import {
    ClassroomManagmentService,
    ClassroomManagmentPopupService,
    ClassroomManagmentComponent,
    ClassroomManagmentDialogComponent,
    ClassroomManagmentPopupComponent,
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
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClassroomManagmentComponent,
        ClassroomManagmentDialogComponent,
        ClassroomManagmentPopupComponent,
    ],
    entryComponents: [
        ClassroomManagmentComponent,
        ClassroomManagmentDialogComponent,
        ClassroomManagmentPopupComponent,
    ],
    providers: [
        ClassroomManagmentService,
        ClassroomManagmentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetClassroomMySuffixModule {}
