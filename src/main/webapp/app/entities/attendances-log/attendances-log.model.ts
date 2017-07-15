export class AttendancesLog {
    constructor(
        public id?: number,
        public date?: any,
        public oldGrade?: number,
        public newGrade?: number,
        public reason?: string,
        public teacherId?: number,
        public attendancesId?: number,
    ) {
    }
}
