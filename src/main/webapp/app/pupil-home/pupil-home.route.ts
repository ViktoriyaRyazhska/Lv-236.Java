/**
 * Created by Kolja on 22.05.2017.
 */
import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { PupilHomeComponent } from './';

export const PUPIL_HOME_ROUTE: Route = {
    path: 'pupil-home',
    component:
    PupilHomeComponent,
    data:
    {
        authorities: ['ROLE_PUPIL'],
    },
    canActivate: [UserRouteAccessService]
};
