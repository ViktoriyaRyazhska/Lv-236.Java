import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { LessonType } from './lesson-type.model';
import { LessonTypeService } from './lesson-type.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-lesson-type',
    templateUrl: './lesson-type.component.html'
})
export class LessonTypeComponent implements OnInit, OnDestroy {
lessonTypes: LessonType[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private lessonTypeService: LessonTypeService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['lessonType']);
    }

    loadAll() {
        this.lessonTypeService.query().subscribe(
            (res: Response) => {
                this.lessonTypes = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInLessonTypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: LessonType) {
        return item.id;
    }
    registerChangeInLessonTypes() {
        this.eventSubscriber = this.eventManager.subscribe('lessonTypeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
