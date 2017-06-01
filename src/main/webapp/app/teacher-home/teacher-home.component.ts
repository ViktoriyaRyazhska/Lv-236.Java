import {Component, OnInit, Input} from '@angular/core';
import {Response} from '@angular/http';
import {Principal} from '../shared/auth/principal.service';
import {JhiLanguageService, AlertService, EventManager} from 'ng-jhipster';

import {LessonMySuffix} from '../entities/lesson/lesson-my-suffix.model';
import {TeacherMySuffix} from './../entities/teacher/teacher-my-suffix.model';
import {FormMySuffix} from '../entities/form/form-my-suffix.model';
import {TeacherHomeService} from './teacher-home.service';
import {Subscription} from 'rxjs/Subscription';



@Component({
    selector: 'teacher-home',
    templateUrl: './teacher-home.component.html',
    styles: []
})
export class TeacherHomeComponent implements OnInit {
    currentAccount: any;
    currentTeacher: TeacherMySuffix;
    Lessons: LessonMySuffix[];
    forms: FormMySuffix[];
    eventSubscriber: Subscription;


    constructor(private principal: Principal,
                private jhiLanguageService: JhiLanguageService,
                private teacherHomeService: TeacherHomeService,
                private alertService: AlertService,
                private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.loadCurrentTeacher();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.registerChangeInLessons();
        });
    }
    loadCurrentTeacher(){
        this.teacherHomeService.getCurrentTeacher().subscribe(
            (res: Response) => {
                this.currentTeacher = res.json();
                this.loadLessons(this.currentTeacher.id);
            },
            (res: Response) => this.onError(res.json())
        );
    }

    loadLessons(teacherId: number) {
        this.teacherHomeService.queryLessons(teacherId).subscribe(
            (res: Response) => {
                this.Lessons = res.json();
                this.loadForms(this.currentTeacher.id);
            });
    }

    loadForms(teacherId: number) {
        this.teacherHomeService.queryForm(teacherId).subscribe(
            (res: Response) => {
                this.forms = res.json();
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

}
