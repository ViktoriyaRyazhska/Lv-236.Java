import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolNetSharedModule } from '../../shared';
import {
    UserService,
    UserPopupService,
    UserComponent,
    UserDetailComponent,
    UserDialogComponent,
    UserPopupComponent,
    UserDeletePopupComponent,
    UserDeleteDialogComponent,
    userRoute,
    userPopupRoute,
} from './';

const ENTITY_STATES = [
    ...userRoute,
    ...userPopupRoute,
];

@NgModule({
    imports: [
        SchoolNetSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        UserComponent,
        UserDetailComponent,
        UserDialogComponent,
        UserDeleteDialogComponent,
        UserPopupComponent,
        UserDeletePopupComponent,
    ],
    entryComponents: [
        UserComponent,
        UserDialogComponent,
        UserPopupComponent,
        UserDeleteDialogComponent,
        UserDeletePopupComponent,
    ],
    providers: [
        UserService,
        UserPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SchoolNetUserModule {}
