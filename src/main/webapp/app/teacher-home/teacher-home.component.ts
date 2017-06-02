import {Component, OnInit} from '@angular/core';
import {Response} from '@angular/http';
import {Principal} from '../shared/auth/principal.service';
import {JhiLanguageService, AlertService, EventManager} from 'ng-jhipster';

import {TeacherMySuffix} from './../entities/teacher/teacher-my-suffix.model';
import {LessonMySuffix} from '../entities/lesson/lesson-my-suffix.model';
import {FormMySuffix} from '../entities/form/form-my-suffix.model';
import {TeacherHomeService} from './teacher-home.service';
import {Subscription} from 'rxjs/Subscription';
import {ScheduleMySuffix} from '../entities/schedule/schedule-my-suffix.model';
import {PupilHomeService} from "../pupil-home/pupil-home.service";
import {ScheduleMySuffixService} from "../entities/schedule/schedule-my-suffix.service";

@Component({
    selector: 'teacher-home',
    templateUrl: './teacher-home.component.html',
    styles: []
})
export class TeacherHomeComponent implements OnInit {
    currentAccount: any;
    currentTeacher: TeacherMySuffix;
    lessons: LessonMySuffix[];
    forms: FormMySuffix[];
    schedules: ScheduleMySuffix[];
    currentSchedules: ScheduleMySuffix[];
    eventSubscriber: Subscription;
    selectedDate: Date = new Date(Date.now());
    name: string;

    constructor(private principal: Principal,
                private jhiLanguageService: JhiLanguageService,
                private teacherHomeService: TeacherHomeService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private pupilHomeService: PupilHomeService,
                private scheduleMySuffixService: ScheduleMySuffixService) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.loadCurrentTeacher();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.registerChangeInLessons();
        });
    }

    loadCurrentTeacher() {
        this.teacherHomeService.getCurrentTeacher().subscribe(
            (res: Response) => {
                this.currentTeacher = res.json();
                this.loadLessons(this.currentTeacher.id);
                console.log(this.currentTeacher.schoolId);
            },
            (res: Response) => this.onError(res.json())
        );
    }

    loadLessons(teacherId: number) {
        this.teacherHomeService.queryLessons(teacherId).subscribe(
            (res: Response) => {
                this.lessons = res.json();
                this.loadForms(this.currentTeacher.id);
            });
    }

    loadForms(teacherId: number) {
        this.teacherHomeService.queryForm(teacherId).subscribe(
            (res: Response) => {
                this.forms = res.json();
                this.loadSchedule(this.currentTeacher.id);
            },
            (res: Response) => this.onError(res.json())
        );
    }

    loadSchedule(teacherId: number) {
        this.teacherHomeService.querySchedule(teacherId).subscribe(
            (res: Response) => {
                this.schedules = res.json();
                this.currentSchedules = this.pupilHomeService.getSchedulesForDate(this.selectedDate, this.schedules);

            },
            (res: Response) => this.onError(res.json())
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    registerChangeInLessons() {
        this.eventSubscriber = this.eventManager.subscribe('lessonListModification', (response) => this.loadLessons(this.currentTeacher.id));
    }

    selectHomework(scheduleId: number){
        console.log(scheduleId);
    }
}
