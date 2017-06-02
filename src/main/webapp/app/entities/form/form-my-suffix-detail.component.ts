import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { FormMySuffix } from './form-my-suffix.model';
import { FormMySuffixService } from './form-my-suffix.service';
import { PupilMySuffix } from '../pupil/pupil-my-suffix.model';
import { PupilMySuffixService } from "../pupil/pupil-my-suffix.service";



@Component({
    selector: 'jhi-form-my-suffix-detail',
    templateUrl: './form-my-suffix-detail.component.html'
})
export class FormMySuffixDetailComponent implements OnInit, OnDestroy {
pupils:PupilMySuffix[];
    form: FormMySuffix;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private formService: FormMySuffixService,
        private pupilService: PupilMySuffixService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['form']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInForms();
    }

    load(id) {
        this.formService.find(id).subscribe((form) => {
            this.form = form;
        });

    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInForms() {
        this.eventSubscriber = this.eventManager.subscribe('formListModification', (response) => this.load(this.form.id));
    }}



