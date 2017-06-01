import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';

import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { TeacherManagement } from './teacher-management.model';
import { TeacherManagementService } from './teacher-management.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';





@Component({
    selector: 'jhi-teacher-management',
    templateUrl: './teacher-management.component.html'
})
export class TeacherManagementComponent implements OnInit, OnDestroy {
teachers: TeacherManagement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    authorities: any[];

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private teacherService: TeacherManagementService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,

    ) {
        this.jhiLanguageService.setLocations(['teacher','user-management']);
    }

    loadAll() {
        this.teacherService.query().subscribe(
            (res: Response) => {
                this.teachers = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTeachers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TeacherManagement) {
        return item.id;
    }
    registerChangeInTeachers() {
        this.eventSubscriber = this.eventManager.subscribe('teacherListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
