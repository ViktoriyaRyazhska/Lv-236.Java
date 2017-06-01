/**
 * Created by slavkosoltys on 28.05.17.
 */
import {Injectable} from '@angular/core';
import {Http, Response} from '@angular/http';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class TeacherHomeService {

    private resourceUrlLesson = 'api/lessons/teacher';
    private resourceUrlForm = 'api/forms/teacher';
    private resourceUrlCurrentTeacher = 'api/teachers/current';

    constructor(private http: Http) {
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
}

