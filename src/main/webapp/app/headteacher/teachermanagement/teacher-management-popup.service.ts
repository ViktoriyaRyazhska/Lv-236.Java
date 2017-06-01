import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TeacherManagement } from './teacher-management.model';
import { TeacherManagementService } from './teacher-management.service';
@Injectable()
export class TeacherManagementPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private teacherService: TeacherManagementService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.teacherService.find(id).subscribe((teacher) => {
                this.teacherModalRef(component, teacher);
            });
        } else {
            return this.teacherModalRef(component, new TeacherManagement());// can mistake
        }
    }

    teacherModalRef(component: Component, teacher: TeacherManagement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.teacher = teacher;
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
