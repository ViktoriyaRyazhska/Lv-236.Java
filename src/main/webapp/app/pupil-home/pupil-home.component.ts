/**
 * Created by Kolja on 22.05.2017.
 */
import {Component, OnInit} from '@angular/core';
import { Response } from '@angular/http';
import {Principal} from '../shared';
import {PupilHomeService} from './pupil-home.service';
import {EventManager, JhiLanguageService, AlertService} from 'ng-jhipster';
import {PupilMySuffix} from "../entities/pupil/pupil-my-suffix.model";

@Component({
    selector: 'pupil-home',
    templateUrl: 'pupil-home.component.html',
    styleUrls: ['pupil-home.component.css'],
})
export class PupilHomeComponent implements OnInit {
    account: any;
    currentPupil: PupilMySuffix;
    constructor(private principal: Principal,
                private alertService: AlertService,
                private jhiLanguageService: JhiLanguageService,
                private pupilHomeService: PupilHomeService) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.loadCurrentPupil();
    }

    loadCurrentPupil() {
        this.pupilHomeService.getCurrentPupil().subscribe(
            (res: Response) => {
                this.currentPupil = res.json();
            },
            (res: Response) => this.onError(res.json())
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
