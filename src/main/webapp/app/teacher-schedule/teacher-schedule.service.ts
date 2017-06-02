import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { TeacherSchedule } from './teacher-schedule.model';
import { DateUtils } from 'ng-jhipster';

@Injectable()
export class TeacherScheduleService {

    private resourceUrl = 'api/schedules';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    find(id: number): Observable<TeacherSchedule[]> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.date = this.dateUtils
                .convertDateTimeFromServer(jsonResponse.date);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
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

    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }

    filterSchedule(teacherID: number, date: Date, teacherSchedule: TeacherSchedule[]): TeacherSchedule[] {
        let schedulesForDate: TeacherSchedule[] = [];
        for (let schedule of teacherSchedule) {
            if (date.getDate() === schedule.date.getDate() &&
                date.getMonth() === schedule.date.getMonth() &&
                date.getFullYear() === schedule.date.getFullYear() &&
                teacherID === schedule.teacherId) {
                schedulesForDate.push(schedule);
            }
        }
        return schedulesForDate;
    }

}
