import {Pipe, PipeTransform} from '@angular/core';
import {PupilHomeSchedules} from './pupil-home-schedules.model';

@Pipe ({
    name: 'schedulessort'
})

export class PupilHomeSchedulesSortPipe implements PipeTransform {
    transform(array: PupilHomeSchedules[]) : PupilHomeSchedules[] {
        array.sort((a: PupilHomeSchedules, b: PupilHomeSchedules) => {
            if(a.lessonPosition < b.lessonPosition){
                return -1;
            } else if(a.lessonPosition > b.lessonPosition) {
                return 1;
            } else {
                return 0;
            }
            });
        return array;
    }
}
