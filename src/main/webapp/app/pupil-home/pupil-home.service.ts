/**
 * Created by Kolja on 22.05.2017.
 */
import { Injectable } from '@angular/core';
import {Subject, Observable} from 'rxjs';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { PupilHomeSchedules } from './pupil-home-schedules/pupil-home-schedules.model';
import { DateUtils } from 'ng-jhipster';
import {Lesson} from "./pupil-home-schedules/pupil-home-lesson.model";
@Injectable()
export class PupilHomeService {

    private resourceUrl = 'api/pupilhome';
    private resourceLessonUrl = 'api/pupilhome/distinctform';

    // Observable string sources
    private dateToSend = new Subject<Date>();

    // Observable string streams
    dateToSend$ = this.dateToSend.asObservable();

    constructor(private http: Http, private dateUtils: DateUtils) { }

    findByForm(): Observable<Response> {
        return this.http.get(`${this.resourceUrl}/getschedules`).map((res: Response) =>
            this.convertResponse(res));
    }

    findByFormId(formId: number): Observable<Response> {
        return this.http.get(`${this.resourceUrl}/getschedules/${formId}`).map((res: Response) =>
            this.convertResponse(res));
    }

    //get all distinct lessons for this form
    getDistinctLessons(formId: number): Observable<Response> {
        console.log("get all lessons");
        return this.http.get(`${this.resourceLessonUrl}/${formId}`).map((res: Response) =>
            this.convertResponse(res));
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse[i].date);
        }
        res._body = jsonResponse;
        return res;
    }

    getCurrentPupil(): Observable<Response> {
        return this.http.get(`${this.resourceUrl}/getpupil`);
    }

    // Service message commands
    publishData(data: Date) {
        this.dateToSend.next(data);
    }

    // get schedules array for specified date
    getSchedulesForDate(date: Date, pupilSchedules: PupilHomeSchedules[]): PupilHomeSchedules[] {
        let schedulesForDate: PupilHomeSchedules[] = [];
        for(let schedule of pupilSchedules){
            if (date.getDate() === schedule.date.getDate() &&
                date.getFullYear() === schedule.date.getFullYear() &&
                date.getMonth() === schedule.date.getMonth()) {
                schedulesForDate.push(schedule);
            }
        }
        return schedulesForDate;
    }

}
