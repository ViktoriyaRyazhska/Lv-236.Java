/**
 * Created by inva on 31-May-17.
 */
import {Component, OnInit, OnDestroy} from '@angular/core';
import { Response } from '@angular/http';
import {Principal} from '../shared';
import {EventManager, JhiLanguageService, AlertService} from 'ng-jhipster';
import {AttendancesMySuffix} from '../entities/attendances/attendances-my-suffix.model';
import {Subscription} from "rxjs/Rx";

@Component({
    selector: 'pupil-home-grades',
    templateUrl: 'pupil-home-grades.component.html',
    styleUrls: ['pupil-home-grades.component.css'],
})

export class PupilHomeGradesComponent implements OnInit {
    pupilAttendances: AttendancesMySuffix[] = [];
    account: any;
    eventSubscriber: Subscription;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal
    ) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
