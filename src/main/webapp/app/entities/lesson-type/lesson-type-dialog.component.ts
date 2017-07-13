import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { LessonType } from './lesson-type.model';
import { LessonTypePopupService } from './lesson-type-popup.service';
import { LessonTypeService } from './lesson-type.service';

@Component({
    selector: 'jhi-lesson-type-dialog',
    templateUrl: './lesson-type-dialog.component.html'
})
export class LessonTypeDialogComponent implements OnInit {

    lessonType: LessonType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private lessonTypeService: LessonTypeService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['lessonType']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.lessonType.id !== undefined) {
            this.lessonTypeService.update(this.lessonType)
                .subscribe((res: LessonType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.lessonTypeService.create(this.lessonType)
                .subscribe((res: LessonType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: LessonType) {
        this.eventManager.broadcast({ name: 'lessonTypeListModification', content: 'OK'});
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
    selector: 'jhi-lesson-type-popup',
    template: ''
})
export class LessonTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private lessonTypePopupService: LessonTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.lessonTypePopupService
                    .open(LessonTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.lessonTypePopupService
                    .open(LessonTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
