/**
 * Created by inva on 23-May-17.
 */

import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared';
import { PupilHomeSchedulesComponent} from './pupil-home-schedules.component';

export const PUPIL_HOME_SCHEDULES_ROUTE: Route = {
    path: 'pupil-home/getschedules/',
        component:
        PupilHomeSchedulesComponent,
        data:
        {
            authorities: ['ROLE_PUPIL'],
            pageTitle: 'schoolNetApp.schedule.home.title'
        },
    canActivate: [UserRouteAccessService]
};
