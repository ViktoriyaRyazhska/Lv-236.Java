import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SchoolNetSchoolMySuffixModule } from './school/school-my-suffix.module';
import { SchoolNetFormMySuffixModule } from './form/form-my-suffix.module';
import { SchoolNetPupilMySuffixModule } from './pupil/pupil-my-suffix.module';
import { SchoolNetTeacherMySuffixModule } from './teacher/teacher-my-suffix.module';
import { SchoolNetClassroomMySuffixModule } from './classroom/classroom-my-suffix.module';
import { SchoolNetLessonMySuffixModule } from './lesson/lesson-my-suffix.module';
import { SchoolNetScheduleMySuffixModule } from './schedule/schedule-my-suffix.module';
import { SchoolNetAttendancesMySuffixModule } from './attendances/attendances-my-suffix.module';
import { SchoolNetParentMySuffixModule } from './parent/parent-my-suffix.module';
import { SchoolNetUserAddonModule } from './user-addon/user-addon.module';
import { SchoolNetLessonTypeModule } from './lesson-type/lesson-type.module';
import { SchoolNetAttendancesLogModule } from './attendances-log/attendances-log.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        SchoolNetSchoolMySuffixModule,
        SchoolNetFormMySuffixModule,
        SchoolNetPupilMySuffixModule,
        SchoolNetTeacherMySuffixModule,
        SchoolNetClassroomMySuffixModule,
        SchoolNetLessonMySuffixModule,
        SchoolNetScheduleMySuffixModule,
        SchoolNetAttendancesMySuffixModule,
        SchoolNetParentMySuffixModule,
        SchoolNetUserAddonModule,
        SchoolNetLessonTypeModule,
        SchoolNetAttendancesLogModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetEntityModule {}
