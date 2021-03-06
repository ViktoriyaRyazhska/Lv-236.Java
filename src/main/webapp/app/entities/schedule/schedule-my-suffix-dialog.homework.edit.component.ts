import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {AlertService, EventManager, JhiLanguageService} from 'ng-jhipster';

import {ScheduleMySuffix} from './schedule-my-suffix.model';
import {TeacherHomeService} from '../../teacher-home/teacher-home.service';
import {TeacherHomePopupService} from '../../teacher-home/teacher-home-popup.service';

@Component({
    selector: 'jhi-schedule-my-suffix-dialog',
    templateUrl: './schedule-my-suffix-homework-edit-dialog.component.html'
})
export class ScheduleHomeworkDialogComponent implements OnInit {

    schedule: ScheduleMySuffix;
    authorities: any[];
    isSaving: boolean;

    constructor(public activeModal: NgbActiveModal,
                private jhiLanguageService: JhiLanguageService,
                private alertService: AlertService,
                private teacherHomeService: TeacherHomeService,
                private eventManager: EventManager) {
        this.jhiLanguageService.setLocations(['teacher-home']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_TEACHER'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schedule.id !== undefined) {
            this.teacherHomeService.updateHomework(this.schedule)
                .subscribe((res: ScheduleMySuffix) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ScheduleMySuffix) {
        this.eventManager.broadcast({name: 'scheduleListModification', content: 'OK'});
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
    selector: 'teacher-home-popup-homework',
    template: ''
})
export class ScheduleMySuffixPopupComponentHomework implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(private route: ActivatedRoute,
                private teacherPopupService: TeacherHomePopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.modalRef = this.teacherPopupService
                    .open(ScheduleHomeworkDialogComponent, params['id']);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
