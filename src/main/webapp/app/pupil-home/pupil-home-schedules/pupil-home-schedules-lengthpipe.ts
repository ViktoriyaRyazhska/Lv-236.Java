/**
 * Created by Kolja on 29.05.2017.
 */
import {Pipe} from '@angular/core'
// custom pipe to regulate the output string length
@Pipe({
    name: 'lengthPipe'
})
export class LengthPipe {
    transform(value: string, args: string[]) : string {
        let limit = args.length > 0 ? parseInt(args[0], 10) : 10;
        let trail = args.length > 1 ? args[1] : '...';


        return value.length > limit ? value.substring(0, limit) + trail : value;
    }
}
