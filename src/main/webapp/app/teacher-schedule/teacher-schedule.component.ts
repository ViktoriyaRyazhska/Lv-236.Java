import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { JhiLanguageService, AlertService } from 'ng-jhipster';

import { TeacherSchedule } from './teacher-schedule.model';
import { TeacherScheduleService } from './teacher-schedule.service';
import { TeacherMySuffix } from '../entities/teacher/teacher-my-suffix.model';
import { TeacherMySuffixService } from '../entities/teacher/teacher-my-suffix.service';

import { Principal } from '../shared';

@Component({
    selector: 'jhi-teacher-schedule',
    templateUrl: './teacher-schedule.component.html',
    styleUrls: ['./teacher-schedule.component.css']
})
export class TeacherScheduleComponent implements OnInit {
    currentAccount: any;
    teachers: TeacherMySuffix[];
    teacherSchedules: TeacherSchedule[];
    filteredSchedules: TeacherSchedule[];
    selectedID: string;
    selectedDate: string;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private teacherService: TeacherMySuffixService,
        private teacherScheduleService: TeacherScheduleService,
        private alertService: AlertService,
        private principal: Principal,
    ) {
        this.jhiLanguageService.setLocations([]);
        this.filteredSchedules = [];
        this.selectedID = '1';
        this.selectedDate = Date.now().toString();
    }

    loadAll() {
        this.teacherService.query().subscribe(
            (res: Response) => {
                this.teachers = res.json();
            },
            (res: Response) => {
                this.onError(res.json());
            }
        );
        this.teacherScheduleService.query().subscribe(
            (res: Response) => {
                this.teacherSchedules = res.json();
                this.onFormChange();
            },
            (res: Response) => {
                this.onError(res.json());
            }
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    onFormChange() {
        console.log('onTeacherSelect() with TeacherID ' + this.selectedID);
        console.log('onDateChange() ' + this.selectedDate);
        this.filteredSchedules = this.teacherScheduleService.filterSchedule(parseInt(this.selectedID, 10), new Date(this.selectedDate.toString()), this.teacherSchedules);
    }
}
