import { Component, OnInit } from '@angular/core';
import { Response } from '@angular/http';
import { JhiLanguageService, AlertService } from 'ng-jhipster';
import { ScheduleMySuffix } from './../entities/schedule/';
import { TeacherMySuffix } from '../entities/teacher/teacher-my-suffix.model';
import { TeacherScheduleService } from './teacher-schedule.service';
import { INgxMyDpOptions, IMyDateModel } from 'ngx-mydatepicker';

@Component({
    selector: 'jhi-teacher-schedule',
    templateUrl: './teacher-schedule.component.html',
    styleUrls: ['./teacher-schedule.component.css']
})
export class TeacherScheduleComponent implements OnInit {

    currentTeacherAccount: TeacherMySuffix;
    teachers: TeacherMySuffix[] = [];
    allSchedules: ScheduleMySuffix[] = [];
    resultSchedule = [];
    selectedID: string;
    selectedPeriod: string = 'week';
    dateString: string = new Date(Date.now()).toString();
    dateObject: Object;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private teacherScheduleService: TeacherScheduleService,
        private alertService: AlertService,
    ) {
        this.jhiLanguageService.setLocations(['teacher-schedule']);
    }

    private datepickerOptions: INgxMyDpOptions;

    setOptions() {
        let date = new Date(Date.now());
        let minYear, maxYear;

        if (date.getMonth() + 1 >= 9) {
            minYear = date.getFullYear();
            maxYear = minYear - 1;
        } else {
            maxYear = date.getFullYear();
            minYear = maxYear - 1;
        }

        this.datepickerOptions = {
            dateFormat: 'dd.mm.yyyy',
            yearSelector: false,
            disableUntil: {year: minYear, month: 8, day: 31},
            disableSince: {year: maxYear, month: 9, day: 1},
            closeSelectorOnDateSelect: false
        };
    }

    setDate(): void {
        let date = new Date(Date.now());

        this.dateObject = {
            date: {
                year: date.getFullYear(),
                month: date.getMonth() + 1,
                day: date.getDate()}
        };
    }

    onDateChanged(event: IMyDateModel): void {
        this.dateString = event.jsdate.toString();
        this.filterSchedule();
    }

    ngOnInit() {
        this.setDate();
        this.loadCurrentTeacher();
        this.setOptions();
    }

    loadCurrentTeacher() {
        this.teacherScheduleService.getCurrentTeacher().subscribe(
            (res: Response) => {
                this.currentTeacherAccount = res.json();
                this.selectedID = String(this.currentTeacherAccount.id);
                this.loadTeachers();
                this.loadSchedule(this.currentTeacherAccount.schoolId);
            },
            (res: Response) => this.onError(res.json())
        );
    }

    loadTeachers() {
        this.teacherScheduleService.getAllTeachersByCurrentTeacher().subscribe(
            (res: Response) => {
                this.teachers = res.json();
            });
    }

    loadSchedule(schoolId: number) {
        this.teacherScheduleService.getSchedulesBySchoolId(schoolId).subscribe(
            (res: Response) => {
                this.allSchedules = res.json();
                this.filterSchedule();
            },
            (res: Response) => {
                this.onError(res.json());
            }
        );
    }

    filterSchedule() {
        if (this.selectedPeriod == 'day') {
            this.makeDaySchedule();
        }
        if (this.selectedPeriod == 'week') {
            this.makeWeekSchedule();
        }
    }

    makeDaySchedule() {
        let filteredSchedule = this.teacherScheduleService.filterDaySchedule(parseInt(this.selectedID, 10), new Date(this.dateString), this.allSchedules);
        let daySchedule: ScheduleMySuffix[] = [];

        for(let i = 1; i <= 10; i++) {
            let blankSchedule = new ScheduleMySuffix();
            blankSchedule.lessonPosition = i;
            daySchedule.push(blankSchedule);
        }

        for(let i = 0; i < filteredSchedule.length; i++) {
            let lessonPosition = filteredSchedule[i].lessonPosition;
            daySchedule[lessonPosition-1] = filteredSchedule[i];
        }

        this.resultSchedule = daySchedule;
    }

    makeWeekSchedule() {
        let filteredSchedule = this.teacherScheduleService.filterWeekSchedule(parseInt(this.selectedID, 10), new Date(this.dateString), this.allSchedules);
        let weekSchedule = [];
        let daysInWeek = 7;
        let tempDate = this.teacherScheduleService.getMonday(new Date(this.dateString));

        for (let i = 1; i <= daysInWeek; i++) {
            let item = {};

            item["day"] = tempDate;
            item["schedule"] = [];

            for(let i = 1; i <= 10; i++) {
                let blankSchedule = new ScheduleMySuffix();
                blankSchedule.lessonPosition = i;
                item["schedule"].push(blankSchedule);
            }

            weekSchedule.push(item);
            tempDate = this.teacherScheduleService.addDays(tempDate, 1);
        }

        for (let i = 0; i < filteredSchedule.length; i++) {
            let day = (filteredSchedule[i].date.getDay() - 1 + daysInWeek) % daysInWeek;
            let schedule = filteredSchedule[i];
            let lessonPosition = filteredSchedule[i].lessonPosition - 1;

            weekSchedule[day].schedule[lessonPosition] = schedule;
        }

        this.resultSchedule = weekSchedule;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
