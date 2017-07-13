import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AttendancesLog } from './attendances-log.model';
import { AttendancesLogService } from './attendances-log.service';
@Injectable()
export class AttendancesLogPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private attendancesLogService: AttendancesLogService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.attendancesLogService.find(id).subscribe((attendancesLog) => {
                attendancesLog.date = this.datePipe
                    .transform(attendancesLog.date, 'yyyy-MM-ddThh:mm');
                this.attendancesLogModalRef(component, attendancesLog);
            });
        } else {
            return this.attendancesLogModalRef(component, new AttendancesLog());
        }
    }

    attendancesLogModalRef(component: Component, attendancesLog: AttendancesLog): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.attendancesLog = attendancesLog;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
