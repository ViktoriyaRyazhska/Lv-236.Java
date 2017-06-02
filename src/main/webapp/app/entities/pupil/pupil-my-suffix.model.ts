

import {ParentMySuffix} from "../parent/parent-my-suffix.model";
export class PupilMySuffix {
    constructor(
        public id?: number,
        public enabled?: boolean,
        public userId?: number,
        public attendancesId?: number,
        public formId?: number,
        public parents?: ParentMySuffix[],

    ) {
        this.enabled = false;
    }
}
