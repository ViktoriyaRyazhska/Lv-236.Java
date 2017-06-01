/**
 * Created by slavkosoltys on 28.05.17.
 */
import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import {TeacherHomeComponent} from './teacher-home.component';

export const TeacherHomeRoute: Route = {
    path: 'teacher-home',
    component: TeacherHomeComponent,
    data:
        {
            authorities: ['ROLE_TEACHER'],
        },
    canActivate: [UserRouteAccessService]
};
