import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ScheduleMySuffix } from './schedule-my-suffix.model';
import { ScheduleMySuffixPopupService } from './schedule-my-suffix-popup.service';
import { ScheduleMySuffixService } from './schedule-my-suffix.service';
import { LessonMySuffix, LessonMySuffixService } from '../lesson';
import { FormMySuffix, FormMySuffixService } from '../form';
import { ClassroomMySuffix, ClassroomMySuffixService } from '../classroom';
import { TeacherMySuffix, TeacherMySuffixService } from '../teacher';
import { LessonType, LessonTypeService } from '../lesson-type';

@Component({
    selector: 'jhi-schedule-my-suffix-dialog',
    templateUrl: './schedule-my-suffix-dialog.component.html'
})
export class ScheduleMySuffixDialogComponent implements OnInit {

    schedule: ScheduleMySuffix;
    authorities: any[];
    isSaving: boolean;

    lessons: LessonMySuffix[];

    forms: FormMySuffix[];

    classrooms: ClassroomMySuffix[];

    teachers: TeacherMySuffix[];

    lessontypes: LessonType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private scheduleService: ScheduleMySuffixService,
        private lessonService: LessonMySuffixService,
        private formService: FormMySuffixService,
        private classroomService: ClassroomMySuffixService,
        private teacherService: TeacherMySuffixService,
        private lessonTypeService: LessonTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedule']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.lessonService.query().subscribe(
            (res: Response) => { this.lessons = res.json(); }, (res: Response) => this.onError(res.json()));
        this.formService.query().subscribe(
            (res: Response) => { this.forms = res.json(); }, (res: Response) => this.onError(res.json()));
        this.classroomService.query().subscribe(
            (res: Response) => { this.classrooms = res.json(); }, (res: Response) => this.onError(res.json()));
        this.teacherService.query().subscribe(
            (res: Response) => { this.teachers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.lessonTypeService.query().subscribe(
            (res: Response) => { this.lessontypes = res.json(); }, (res: Response) => this.onError(res.json()));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schedule.id !== undefined) {
            this.scheduleService.update(this.schedule)
                .subscribe((res: ScheduleMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.scheduleService.create(this.schedule)
                .subscribe((res: ScheduleMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ScheduleMySuffix) {
        this.eventManager.broadcast({ name: 'scheduleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackLessonById(index: number, item: LessonMySuffix) {
        return item.id;
    }

    trackFormById(index: number, item: FormMySuffix) {
        return item.id;
    }

    trackClassroomById(index: number, item: ClassroomMySuffix) {
        return item.id;
    }

    trackTeacherById(index: number, item: TeacherMySuffix) {
        return item.id;
    }

    trackLessonTypeById(index: number, item: LessonType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-schedule-my-suffix-popup',
    template: ''
})
export class ScheduleMySuffixPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schedulePopupService: ScheduleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.schedulePopupService
                    .open(ScheduleMySuffixDialogComponent, params['id']);
            } else {
                this.modalRef = this.schedulePopupService
                    .open(ScheduleMySuffixDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
