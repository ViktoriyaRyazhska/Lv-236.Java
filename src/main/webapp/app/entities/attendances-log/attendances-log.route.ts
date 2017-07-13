import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AttendancesLogComponent } from './attendances-log.component';
import { AttendancesLogDetailComponent } from './attendances-log-detail.component';
import { AttendancesLogPopupComponent } from './attendances-log-dialog.component';
import { AttendancesLogDeletePopupComponent } from './attendances-log-delete-dialog.component';

import { Principal } from '../../shared';

export const attendancesLogRoute: Routes = [
  {
    path: 'attendances-log',
    component: AttendancesLogComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.attendancesLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'attendances-log/:id',
    component: AttendancesLogDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.attendancesLog.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const attendancesLogPopupRoute: Routes = [
  {
    path: 'attendances-log-new',
    component: AttendancesLogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.attendancesLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'attendances-log/:id/edit',
    component: AttendancesLogPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.attendancesLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'attendances-log/:id/delete',
    component: AttendancesLogDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.attendancesLog.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
