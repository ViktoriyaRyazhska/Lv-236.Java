import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { UserAddon } from './user-addon.model';

@Injectable()
export class UserAddonService {

    private resourceUrl = 'api/user-addons';
    private resourceMy = 'api/user-my';

    constructor(private http: Http) { }

    create(userAddon: UserAddon): Observable<UserAddon> {
        const copy: UserAddon = Object.assign({}, userAddon);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(userAddon: UserAddon): Observable<UserAddon> {
        const copy: UserAddon = Object.assign({}, userAddon);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    saveMy(userAddon: UserAddon): Observable<UserAddon> {
        const copy: UserAddon = Object.assign({}, userAddon);
        return this.http.post(this.resourceMy, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<UserAddon> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    findMy(): Observable<UserAddon> {
        return this.http.get(`${this.resourceMy}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
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
}
