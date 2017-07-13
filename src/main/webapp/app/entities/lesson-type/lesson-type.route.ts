import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LessonTypeComponent } from './lesson-type.component';
import { LessonTypeDetailComponent } from './lesson-type-detail.component';
import { LessonTypePopupComponent } from './lesson-type-dialog.component';
import { LessonTypeDeletePopupComponent } from './lesson-type-delete-dialog.component';

import { Principal } from '../../shared';

export const lessonTypeRoute: Routes = [
  {
    path: 'lesson-type',
    component: LessonTypeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.lessonType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }, {
    path: 'lesson-type/:id',
    component: LessonTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.lessonType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const lessonTypePopupRoute: Routes = [
  {
    path: 'lesson-type-new',
    component: LessonTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.lessonType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'lesson-type/:id/edit',
    component: LessonTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.lessonType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  },
  {
    path: 'lesson-type/:id/delete',
    component: LessonTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'schoolNetApp.lessonType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
