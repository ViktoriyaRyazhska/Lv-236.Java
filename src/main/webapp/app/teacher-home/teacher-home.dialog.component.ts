/**
 * Created by slavkosoltys on 01.06.17.
 */
import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';
import {ScheduleMySuffixService} from "../entities/schedule/schedule-my-suffix.service";
import {ScheduleMySuffix} from "../entities/schedule/schedule-my-suffix.model";
import {ScheduleMySuffixPopupService} from "../entities/schedule/schedule-my-suffix-popup.service";


@Component({
    selector: 'teacher-home-dialog',
    templateUrl: './teacher-home-dialog.component.html'
})
export class TeacherDialogComponent implements OnInit {

    schedule: ScheduleMySuffix;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private scheduleService: ScheduleMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['schedule']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_TEACHER', 'ROLE_ADMIN'];
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


}

@Component({
    selector: 'jhi-schedule-my-suffix-popup',
    template: ''
})
export class TeacherHomePopupComponent implements OnInit, OnDestroy {

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
                    .open(TeacherDialogComponent, params['id']);
            } else {
                this.modalRef = this.schedulePopupService
                    .open(TeacherDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
