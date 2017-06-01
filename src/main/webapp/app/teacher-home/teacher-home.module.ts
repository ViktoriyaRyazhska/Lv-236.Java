/**
 * Created by slavkosoltys on 28.05.17.
 */
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'angular-calendar';

import { SchoolNetSharedModule } from '../shared';

import {TeacherHomeComponent} from "./teacher-home.component";
import {TeacherHomeRoute} from "./teacher-home.route";
import {TeacherHomeService} from "./teacher-home.service";
import {LessonMySuffixComponent} from "../entities/lesson/lesson-my-suffix.component";
import {FormMySuffixComponent} from "../entities/form/form-my-suffix.component";

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        HttpModule,
        SchoolNetSharedModule,
        RouterModule.forRoot([ TeacherHomeRoute ], { useHash: true }),
    ],
    declarations: [
        TeacherHomeComponent
    ],
    entryComponents: [
        TeacherHomeComponent
    ],
    providers: [
        TeacherHomeService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class SchoolNetTeacherHomeModule {}
