import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { TeacherManagementComponent } from './teacher-management.component';

import { Principal } from '../../shared';
import {TeacherManagementPopupComponent} from "./teacher-management-dialog.component";

export const TEACHER_ROUTE: Routes = [
  {
    path: 'headteacher-management',
    component: TeacherManagementComponent,
    data: {
        authorities: ['ROLE_HEAD_TEACHER', 'ROLE_ADMIN'],
        pageTitle: 'schoolNetApp.school.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const TEACHER_POPUP_ROUTE: Routes = [
    {
        path: 'teacher-management-new',
        component: TeacherManagementPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN','ROLE_HEAD_TEACHER'],
            pageTitle: 'schoolNetApp.teacher.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
