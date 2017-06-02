/**
 * Created by inva on 31-May-17.
 */
import {Component, OnInit, OnDestroy, Input, Inject} from '@angular/core';
import { Response } from '@angular/http';
import { EventManager, JhiLanguageService, AlertService } from 'ng-jhipster';
import { AttendancesMySuffix } from '../../entities/attendances/attendances-my-suffix.model';
import { Subscription } from 'rxjs/Rx';
import { LessonMySuffix } from '../../entities/lesson/lesson-my-suffix.model';
import { PupilMySuffix } from '../../entities/pupil/pupil-my-suffix.model';
import { PupilHomeService } from '../pupil-home.service';
import { PupilHomeComponent } from '../pupil-home.component';
import { Principal } from '../../shared/auth/principal.service';

@Component({
    selector: 'jhi-pupil-home-grades',
    templateUrl: 'pupil-home-grades.component.html',
    styleUrls: ['pupil-home-grades.component.css'],
})

export class PupilHomeGradesComponent {
    pupilAttendances: AttendancesMySuffix[] = [];
    pupilLessons: LessonMySuffix[] = [];
    account: any;
    eventSubscriber: Subscription;
    currentPupil: PupilMySuffix = new PupilMySuffix();

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private eventManager: EventManager,
        private principal: Principal,
        private pupilHomeService: PupilHomeService
    ) {
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.currentPupil = this.pupilHomeService.getPupil();
        this.loadDistinctLessons(this.currentPupil.formId);
    }

    //load all distinct lessons into pupilLessons
    loadDistinctLessons(formId: number){
        this.pupilHomeService.getDistinctLessons(formId).subscribe(
            (res: Response) => {
                this.pupilLessons = res.json();
            },
        );
    }

    //load all distinct lessons into pupilLessons
    findAllByPupilAndLessonId(pupilId: number, lessonId: number){
        console.log('req to get all attendances for pupil '+pupilId + ' and lessonId '+lessonId);
        this.pupilHomeService.findAllByPupilAndLessonId(pupilId, lessonId).subscribe(
            (res: Response) => {
                this.pupilAttendances = res.json();
            },
        );
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

}
