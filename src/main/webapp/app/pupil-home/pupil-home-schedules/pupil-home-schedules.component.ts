import {Component, OnInit, OnDestroy, Input, OnChanges, ChangeDetectionStrategy} from '@angular/core';
import {Response} from '@angular/http';
import {Subscription} from 'rxjs/Rx';
import {EventManager, JhiLanguageService, AlertService} from 'ng-jhipster';

import {PupilHomeSchedules} from './pupil-home-schedules.model';
import {PupilHomeService} from '../pupil-home.service';
import {ITEMS_PER_PAGE, Principal} from '../../shared';
import {PupilMySuffix} from "../../entities/pupil/pupil-my-suffix.model";
import {AttendancesMySuffix} from "../../entities/attendances/attendances-my-suffix.model";
import {Lesson} from "./pupil-home-lesson.model";
import {PupilHomeComponent} from "../pupil-home.component";
// service to retrieve schedules for pupil
@Component({
    selector: 'jhi-pupil-home-schedules',
    templateUrl: './pupil-home-schedules.component.html',
})
export class PupilHomeSchedulesComponent implements OnInit {
    @Input() currentPupil: PupilMySuffix;
    attendances: AttendancesMySuffix[] = [];
    pupilSchedules: PupilHomeSchedules[] = [];
    pupilLessons: Lesson[] = [];
    account: any;
    eventSubscriber: Subscription;
    currentSchedules: PupilHomeSchedules[] = [];
    schedulesWithBlanks: PupilHomeSchedules[] = [];

    selectedDate: Date = new Date(Date.now());

    // values to show selected homework
    selectedHomework: string = null;
    isSelectedHomework: boolean = false;

    selectHomework(homework: string): void {
        if (this.isSelectedHomework) {
            this.selectedHomework = null;
            this.isSelectedHomework = false;
        }else {
            this.selectedHomework = homework;
            this.isSelectedHomework = true;
        }
    }

    constructor(private jhiLanguageService: JhiLanguageService,
                private pupilHomeService: PupilHomeService,
                private alertService: AlertService,
                private eventManager: EventManager,
                private principal: Principal,
                private pupilHomeComponent: PupilHomeComponent) {

        // subscribe on changes in calendar
        this.pupilHomeService.dateToSend$.subscribe(
            (data) => {
                this.selectedDate = data;

                // update schedules when new date is selected
                this.schedulesWithBlanks = this.getSchedulesWithBlanks();
            });
        this.jhiLanguageService.setLocations(['home']);
    }

    ngOnInit() {
        this.loadByFormId();
        this.loadDistinctLessons(7);
        this.findAllByPupilAndLessonId(6, 7);
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
        this.pupilHomeService.findAllByPupilAndLessonId(pupilId, lessonId).subscribe(
            (res: Response) => {
                this.attendances = res.json();
            },
        );
    }

    // function to load schedules for form for current user(if he is pupil)
    loadByFormId() {
        this.pupilHomeService.findByFormId(this.pupilHomeComponent.currentPupil.formId).subscribe(
            (res: Response) => {
                this.pupilSchedules = res.json();
                // initialize schedules for today
                this.currentSchedules = this.pupilHomeService.getSchedulesForDate(this.selectedDate, this.pupilSchedules);
            },
            (res: Response) => this.onError(res.json())
        );
    }

    trackId(index: number, item: PupilHomeSchedules) {
        return item.id;
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    // get an array of schedules fom pupilSchedules for selectedDate
    getSchedules(): PupilHomeSchedules[] {
        return this.generateArray(this.pupilHomeService.getSchedulesForDate(this.selectedDate, this.pupilSchedules));
    }

    // helper method to make new array iterable
    generateArray(obj) {
        return Object.keys(obj).map((key) => obj[key] );
    }

    //generate schedules table with blank fields(where there is no lesson at this position)
    getSchedulesWithBlanks(): PupilHomeSchedules[] {
        this.schedulesWithBlanks = [];
        let currentSched = this.getSchedules();
        for (var i = 1; i < 11; i++) {
            let match = false;
            for (var j = 0; j < currentSched.length; j++) {
                if(currentSched[j].lessonPosition === i) {
                    this.schedulesWithBlanks.push(currentSched[j]);
                    match = true;
                }
            }
            if (match === false) {
                let blankSchedule = new PupilHomeSchedules(null, null, "n/a", i, true, null, null, null, null, null, "n/a", null, "n/a", "n/a", "n/a");
                this.schedulesWithBlanks.push(blankSchedule);
            }
        }
        return this.schedulesWithBlanks;
    }

}
