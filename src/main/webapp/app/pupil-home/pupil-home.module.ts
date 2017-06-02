/**
 * Created by Kolja on 22.05.2017.
 */
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule } from 'angular-calendar';
import { CalendarComponent } from './calendar-component/calendar.component';

import { SchoolNetSharedModule } from '../shared';
import { PupilHomeService } from './pupil-home.service';
import { LengthPipe } from './pupil-home-schedules/pupil-home-schedules-lengthPipe';
import { PupilHomeSchedulesSortPipe } from './pupil-home-schedules/pupil-home-schedules-sortpipe';

import { PUPIL_HOME_ROUTE, PupilHomeComponent, PupilHomeSchedulesComponent } from './';
import { PUPIL_HOME_GRADES_ROUTE } from './pupil-home-grades/pupil-home-grades.route';
import { PupilHomeGradesComponent } from './pupil-home-grades/pupil-home-grades.component';
import { PupilHomeGradesPipe } from './pupil-home-grades/pupil-home-grades-pipe';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        HttpModule,
        BrowserAnimationsModule,
        CalendarModule.forRoot(),
        SchoolNetSharedModule,
        RouterModule.forRoot([ PUPIL_HOME_GRADES_ROUTE ], { useHash: true }),
        RouterModule.forRoot([ PUPIL_HOME_ROUTE ], { useHash: true }),
    ],
    declarations: [
        LengthPipe,
        PupilHomeSchedulesSortPipe,
        PupilHomeGradesPipe,
        CalendarComponent,
        PupilHomeComponent,
        PupilHomeSchedulesComponent,
        PupilHomeGradesComponent,
    ],
    entryComponents: [
        PupilHomeComponent,
        PupilHomeSchedulesComponent,
        PupilHomeGradesComponent,

    ],
    providers: [
        PupilHomeService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class SchoolNetPupilHomeModule {}
