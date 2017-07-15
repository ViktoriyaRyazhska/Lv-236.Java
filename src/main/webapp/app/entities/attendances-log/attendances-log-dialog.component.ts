import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { AttendancesLog } from './attendances-log.model';
import { AttendancesLogPopupService } from './attendances-log-popup.service';
import { AttendancesLogService } from './attendances-log.service';
import { TeacherMySuffix, TeacherMySuffixService } from '../teacher';
import { AttendancesMySuffix, AttendancesMySuffixService } from '../attendances';

@Component({
    selector: 'jhi-attendances-log-dialog',
    templateUrl: './attendances-log-dialog.component.html'
})
export class AttendancesLogDialogComponent implements OnInit {

    attendancesLog: AttendancesLog;
    authorities: any[];
    isSaving: boolean;

    teachers: TeacherMySuffix[];

    attendances: AttendancesMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private attendancesLogService: AttendancesLogService,
        private teacherService: TeacherMySuffixService,
        private attendancesService: AttendancesMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['attendancesLog']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.teacherService.query().subscribe(
            (res: Response) => { this.teachers = res.json(); }, (res: Response) => this.onError(res.json()));
        this.attendancesService.query().subscribe(
            (res: Response) => { this.attendances = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.attendancesLog.id !== undefined) {
            this.attendancesLogService.update(this.attendancesLog)
                .subscribe((res: AttendancesLog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.attendancesLogService.create(this.attendancesLog)
                .subscribe((res: AttendancesLog) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: AttendancesLog) {
        this.eventManager.broadcast({ name: 'attendancesLogListModification', content: 'OK'});
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

    trackTeacherById(index: number, item: TeacherMySuffix) {
        return item.id;
    }

    trackAttendancesById(index: number, item: AttendancesMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-attendances-log-popup',
    template: ''
})
export class AttendancesLogPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendancesLogPopupService: AttendancesLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.attendancesLogPopupService
                    .open(AttendancesLogDialogComponent, params['id']);
            } else {
                this.modalRef = this.attendancesLogPopupService
                    .open(AttendancesLogDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
