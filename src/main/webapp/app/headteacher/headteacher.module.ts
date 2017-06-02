import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';


import { SchoolNetTeacherManagementModule } from './teachermanagement/teacher-management.module';
import { SchoolNetClassroomManagementModule } from './classroommanagement/classroom-management.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SchoolNetTeacherManagementModule,
        SchoolNetClassroomManagementModule,
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetHeadTeacherModule {}
