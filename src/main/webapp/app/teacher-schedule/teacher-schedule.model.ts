export class TeacherSchedule {
    constructor(
        public id?: number,
        public date?: any,
        public homework?: string,
        public lessonPosition?: number,
        public enabled?: boolean,
        public attendancesId?: number,
        public lessonId?: number,
        public formId?: number,
        public classroomId?: number,
        public teacherId?: number,
        public lessonName?: string,
        public formName?: string,
        public classroomName?: string,
    ) {
        this.enabled = false;
    }
}
