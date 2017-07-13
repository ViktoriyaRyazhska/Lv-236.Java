import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { LessonType } from './lesson-type.model';
import { LessonTypeService } from './lesson-type.service';

@Component({
    selector: 'jhi-lesson-type-detail',
    templateUrl: './lesson-type-detail.component.html'
})
export class LessonTypeDetailComponent implements OnInit, OnDestroy {

    lessonType: LessonType;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private lessonTypeService: LessonTypeService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['lessonType']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLessonTypes();
    }

    load(id) {
        this.lessonTypeService.find(id).subscribe((lessonType) => {
            this.lessonType = lessonType;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLessonTypes() {
        this.eventSubscriber = this.eventManager.subscribe('lessonTypeListModification', (response) => this.load(this.lessonType.id));
    }
}
