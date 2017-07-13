import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { AttendancesLog } from './attendances-log.model';
import { AttendancesLogPopupService } from './attendances-log-popup.service';
import { AttendancesLogService } from './attendances-log.service';

@Component({
    selector: 'jhi-attendances-log-delete-dialog',
    templateUrl: './attendances-log-delete-dialog.component.html'
})
export class AttendancesLogDeleteDialogComponent {

    attendancesLog: AttendancesLog;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private attendancesLogService: AttendancesLogService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['attendancesLog']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.attendancesLogService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'attendancesLogListModification',
                content: 'Deleted an attendancesLog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-attendances-log-delete-popup',
    template: ''
})
export class AttendancesLogDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private attendancesLogPopupService: AttendancesLogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.attendancesLogPopupService
                .open(AttendancesLogDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
