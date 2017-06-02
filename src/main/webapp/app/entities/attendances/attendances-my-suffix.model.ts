export class AttendancesMySuffix {
    constructor(
        public id?: number,
        public grade?: number,
        public enabled?: boolean,
        public pupilId?: number,
        public scheduleId?: number,
        public date?: any,
    ) {
        this.enabled = false;
    }
}
