/**
 * Created by inva on 02-Jun-17.
 */
import { Pipe } from '@angular/core'
@Pipe({
    name: 'gradesPipe'
})
export class PupilHomeGradesPipe {
    transform(value: number): any {
        if (value === 0) {
            return 'H';
        }
        if (value === null) {
            return '-';
        }
        return value;
    }
}
