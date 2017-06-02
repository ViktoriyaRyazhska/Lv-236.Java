import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { FormMySuffix } from './form-my-suffix.model';
import { FormMySuffixPopupService } from './form-my-suffix-popup.service';
import { FormMySuffixService } from './form-my-suffix.service';


@Component({
    selector: 'jhi-form-my-suffix-delete-dialog',
    templateUrl: './form-my-suffix-delete-dialog.component.html'
})
export class FormMySuffixDeleteDialogComponent {

    form: FormMySuffix;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private formService: FormMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['form']);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.formService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'formListModification',
                content: 'Deleted an form'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-form-my-suffix-delete-popup',
    template: ''
})
export class FormMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private formPopupService: FormMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.formPopupService
                .open(FormMySuffixDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
