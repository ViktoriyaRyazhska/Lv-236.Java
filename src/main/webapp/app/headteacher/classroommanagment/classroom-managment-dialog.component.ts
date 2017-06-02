import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { ClassroomManagment } from './classroom-managment.model';
import { ClassroomManagmentPopupService } from './classroom-managment-popup.service';
import { ClassroomManagmentService } from './classroom-managment.service';
import { SchoolMySuffix, SchoolMySuffixService } from '../../entities/school';

@Component({
    selector: 'jhi-classroom-managment-dialog',
    templateUrl: './classroom-mangement-dialog.component.html'
})
export class ClassroomManagmentDialogComponent implements OnInit {

    classroom: ClassroomManagment;
    authorities: any[];
    isSaving: boolean;

    schools: SchoolMySuffix[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private classroomService: ClassroomManagmentService,
        private schoolService: SchoolMySuffixService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['classroom']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.schoolService.query().subscribe(
            (res: Response) => { this.schools = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.classroom.id !== undefined) {
            this.classroomService.update(this.classroom)
                .subscribe((res: ClassroomManagment) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.classroomService.create(this.classroom)
                .subscribe((res: ClassroomManagment) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ClassroomManagment) {
        this.eventManager.broadcast({ name: 'classroomListModification', content: 'OK'});
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

    trackSchoolById(index: number, item: SchoolMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-classroom-managment-popup',
    template: ''
})
export class ClassroomManagmentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private classroomPopupService: ClassroomManagmentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.classroomPopupService
                    .open(ClassroomManagmentDialogComponent, params['id']);
            } else {
                this.modalRef = this.classroomPopupService
                    .open(ClassroomManagmentDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
