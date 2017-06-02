import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: "sortPipe"
})
 export  class SortPipe implements PipeTransform{
    transform(value: any[]) : any {
        return  value.sort().reverse();
    }
}

