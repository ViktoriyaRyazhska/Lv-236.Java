/**
 * Created by Kolja on 22.05.2017.
 */
import {Component, OnInit} from '@angular/core';
import { Response } from '@angular/http';
import { Principal } from '../shared';
import { PupilHomeService } from './pupil-home.service';
import { EventManager, JhiLanguageService, AlertService } from 'ng-jhipster';
import { PupilMySuffix } from '../entities/pupil/pupil-my-suffix.model';
import { PupilHomeGradesComponent } from './pupil-home-grades/pupil-home-grades.component';

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
        this.pupilHomeService.loadCurrentPupil().subscribe(
            (res: Response) => {
                this.currentPupil = res.json();
                this.pupilHomeService.setPupil(this.currentPupil);
            },
            (res: Response) => this.onError(res.json())
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
