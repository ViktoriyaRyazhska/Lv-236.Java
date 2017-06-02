/**
 * Created by Kolja on 31.05.2017.
 */
export class Lesson {
    constructor(
        public id?: number,
        public name?: string,
        public enabled?: boolean,
    ) {
        this.enabled = false;
    }
}
