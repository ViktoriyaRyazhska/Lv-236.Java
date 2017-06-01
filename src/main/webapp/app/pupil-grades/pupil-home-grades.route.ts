/**
 * Created by inva on 31-May-17.
 */
import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { PupilHomeGradesComponent } from './';

export const PUPIL_HOME_GRADES_ROUTE: Route = {
    path: 'pupil-home-grades',
    component:
    PupilHomeGradesComponent,
    data:
        {
            authorities: ['ROLE_PUPIL'],
        },
    canActivate: [UserRouteAccessService]
};
