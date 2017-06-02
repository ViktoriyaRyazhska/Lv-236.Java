import {PupilMySuffix} from "../pupil/pupil-my-suffix.model";


export class FormMySuffix {
    constructor(
        public id?: number,
        public name?: string,
        public enabled?: boolean,
        public pupilId?: number,
        public scheduleId?: number,
        public schoolId?: number,
        public pupilsId?:PupilMySuffix[],


    ) {
        this.enabled = false;
    }
}
