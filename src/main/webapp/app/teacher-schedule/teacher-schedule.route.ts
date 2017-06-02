import { Routes } from '@angular/router';

import { TeacherScheduleComponent } from './teacher-schedule.component';

export const teacherScheduleRoute: Routes = [
    {
        path: 'teacher-schedule',
        component: TeacherScheduleComponent,
        data: {
            authorities: [],
            pageTitle: 'global.menu.teacher-schedule'
        },
        canActivate: []
    }
];
