import { Component, OnInit, OnDestroy } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager, ParseLinks, PaginationUtil, JhiLanguageService, AlertService } from 'ng-jhipster';

import { AttendancesLog } from './attendances-log.model';
import { AttendancesLogService } from './attendances-log.service';
import { ITEMS_PER_PAGE, Principal } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-attendances-log',
    templateUrl: './attendances-log.component.html'
})
export class AttendancesLogComponent implements OnInit, OnDestroy {
attendancesLogs: AttendancesLog[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private attendancesLogService: AttendancesLogService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['attendancesLog']);
    }

    loadAll() {
        this.attendancesLogService.query().subscribe(
            (res: Response) => {
                this.attendancesLogs = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAttendancesLogs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: AttendancesLog) {
        return item.id;
    }
    registerChangeInAttendancesLogs() {
        this.eventSubscriber = this.eventManager.subscribe('attendancesLogListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
