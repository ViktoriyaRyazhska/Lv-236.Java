export class LessonType {
    constructor(
        public id?: number,
        public enabled?: boolean,
        public name?: string,
    ) {
        this.enabled = false;
    }
}
