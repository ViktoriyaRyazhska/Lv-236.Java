import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../../shared';
import {
    LessonTypeService,
    LessonTypePopupService,
    LessonTypeComponent,
    LessonTypeDetailComponent,
    LessonTypeDialogComponent,
    LessonTypePopupComponent,
    LessonTypeDeletePopupComponent,
    LessonTypeDeleteDialogComponent,
    lessonTypeRoute,
    lessonTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...lessonTypeRoute,
    ...lessonTypePopupRoute,
];

@NgModule({
    imports: [
        SchoolNetSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LessonTypeComponent,
        LessonTypeDetailComponent,
        LessonTypeDialogComponent,
        LessonTypeDeleteDialogComponent,
        LessonTypePopupComponent,
        LessonTypeDeletePopupComponent,
    ],
    entryComponents: [
        LessonTypeComponent,
        LessonTypeDialogComponent,
        LessonTypePopupComponent,
        LessonTypeDeleteDialogComponent,
        LessonTypeDeletePopupComponent,
    ],
    providers: [
        LessonTypeService,
        LessonTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetLessonTypeModule {}
