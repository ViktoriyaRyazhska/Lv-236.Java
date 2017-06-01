import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SchoolNetSharedModule } from '../shared';
import { PUPIL_HOME_GRADES_ROUTE, PupilHomeGradesComponent } from './';

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        HttpModule,
        BrowserAnimationsModule,
        SchoolNetSharedModule,
        RouterModule.forRoot([ PUPIL_HOME_GRADES_ROUTE ], { useHash: true })
    ],
    declarations: [
        PupilHomeGradesComponent,
    ],
    entryComponents: [
        PupilHomeGradesComponent
    ],
    providers: [

    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class SchoolNetPupilHomeGradesModule {}
