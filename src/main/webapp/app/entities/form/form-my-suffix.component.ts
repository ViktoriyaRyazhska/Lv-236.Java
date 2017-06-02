import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { FormMySuffix } from './form-my-suffix.model';
import { FormMySuffixService } from './form-my-suffix.service';
import { PupilMySuffix } from '../pupil/pupil-my-suffix.model';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-form-my-suffix',
    templateUrl: './form-my-suffix.component.html'
})
export class FormMySuffixComponent implements OnInit, OnDestroy {
    pupil: PupilMySuffix;
forms: FormMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private formService: FormMySuffixService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['form']);
    }

    loadAll() {
        this.formService.query().subscribe(
            (res: Response) => {
                this.forms = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInForms();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FormMySuffix) {
        return item.id;
    }
    registerChangeInForms() {
        this.eventSubscriber = this.eventManager.subscribe('formListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
