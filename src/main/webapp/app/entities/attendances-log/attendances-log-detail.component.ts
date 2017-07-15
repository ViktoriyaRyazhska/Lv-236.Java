import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { AttendancesLog } from './attendances-log.model';
import { AttendancesLogService } from './attendances-log.service';

@Component({
    selector: 'jhi-attendances-log-detail',
    templateUrl: './attendances-log-detail.component.html'
})
export class AttendancesLogDetailComponent implements OnInit, OnDestroy {

    attendancesLog: AttendancesLog;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private attendancesLogService: AttendancesLogService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['attendancesLog']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttendancesLogs();
    }

    load(id) {
        this.attendancesLogService.find(id).subscribe((attendancesLog) => {
            this.attendancesLog = attendancesLog;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttendancesLogs() {
        this.eventSubscriber = this.eventManager.subscribe('attendancesLogListModification', (response) => this.load(this.attendancesLog.id));
    }
}
