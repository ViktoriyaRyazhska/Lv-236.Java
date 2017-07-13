import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LessonType } from './lesson-type.model';
import { LessonTypeService } from './lesson-type.service';
@Injectable()
export class LessonTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private lessonTypeService: LessonTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.lessonTypeService.find(id).subscribe((lessonType) => {
                this.lessonTypeModalRef(component, lessonType);
            });
        } else {
            return this.lessonTypeModalRef(component, new LessonType());
        }
    }

    lessonTypeModalRef(component: Component, lessonType: LessonType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.lessonType = lessonType;
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
