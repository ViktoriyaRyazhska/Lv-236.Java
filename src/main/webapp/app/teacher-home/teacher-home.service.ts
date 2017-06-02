/**
 * Created by slavkosoltys on 28.05.17.
 */
import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {DateUtils} from 'ng-jhipster';

@Injectable()
export class TeacherHomeService {

    private resourceUrlLesson = 'api/teacher-home/lessons/teacher';
    private resourceUrlForm = 'api/teacher-home/forms/teacher';
    private resourceUrlCurrentTeacher = 'api/teacher-home/teachers/current';
    private resourceUrlSchedule = 'api/teacher-home/schedules/teacher';

    constructor(private http: Http, private dateUtils: DateUtils) {
    }

    queryLessons(teacherId: number): Observable<Response> {
        return this.http.get(`${this.resourceUrlLesson}/${teacherId}`);
    }

    queryForm(teacherId: number): Observable<Response> {
        return this.http.get(`${this.resourceUrlForm}/${teacherId}`);
    }

    getCurrentTeacher(): Observable<Response> {
        return this.http.get(`${this.resourceUrlCurrentTeacher}`);
    }

    querySchedule(teacherId: number): Observable<Response> {
        return this.http.get(`${this.resourceUrlSchedule}/${teacherId}`)
            .map((res: any) => this.convertResponse(res));
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
}
