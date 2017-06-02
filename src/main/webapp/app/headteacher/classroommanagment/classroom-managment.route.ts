import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClassroomManagmentComponent } from './classroom-managment.component';
import { ClassroomManagmentPopupComponent } from './classroom-managment-dialog.component';

import { Principal } from '../../shared';

export const classroomRoute: Routes = [
  {
    path: 'classroom-managment',
    component: ClassroomManagmentComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.classroom.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const classroomPopupRoute: Routes = [
  {
    path: 'classroom-managment-new',
    component: ClassroomManagmentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.classroom.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'classroom-managment/:id/edit',
    component: ClassroomManagmentPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.classroom.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
];
