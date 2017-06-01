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

import { PUPIL_HOME_ROUTE, PUPIL_HOME_SCHEDULES_ROUTE, PupilHomeComponent, PupilHomeSchedulesComponent } from './';


@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        HttpModule,
        BrowserAnimationsModule,
        CalendarModule.forRoot(),
        SchoolNetSharedModule,
        RouterModule.forRoot([ PUPIL_HOME_ROUTE ], { useHash: true }),
        RouterModule.forRoot([ PUPIL_HOME_SCHEDULES_ROUTE ], { useHash: true })
    ],
    declarations: [
        LengthPipe,
        PupilHomeSchedulesSortPipe,
        CalendarComponent,
        PupilHomeComponent,
        PupilHomeSchedulesComponent,
    ],
    entryComponents: [
        PupilHomeComponent,
        PupilHomeSchedulesComponent
    ],
    providers: [
        PupilHomeService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class SchoolNetPupilHomeModule {}
