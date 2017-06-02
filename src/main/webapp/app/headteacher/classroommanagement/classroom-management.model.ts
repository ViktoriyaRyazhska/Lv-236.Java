export class ClassroomManagement {
    constructor(
        public id?: number,
        public name?: string,
        public enabled?: boolean,
        public scheduleId?: number,
        public schoolId?: number,
    ) {
        this.enabled = false;
    }
}
