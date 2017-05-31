import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService  } from 'ng-jhipster';

import { User } from './user.model';
import { UserService } from './user.service';

@Component({
    selector: 'jhi-user-detail',
    templateUrl: './user-detail.component.html'
})
export class UserDetailComponent implements OnInit, OnDestroy {

    user: User;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private userService: UserService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['user']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUsers();
    }

    load(id) {
        this.userService.find(id).subscribe((user) => {
            this.user = user;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUsers() {
        this.eventSubscriber = this.eventManager.subscribe('userListModification', (response) => this.load(this.user.id));
    }
}
